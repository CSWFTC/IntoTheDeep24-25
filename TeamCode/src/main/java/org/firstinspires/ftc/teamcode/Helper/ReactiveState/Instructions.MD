## Reactive State

Reactive state is when actions occur automatically based on detected "state changes". This is a lightweight framework which makes implementing reactive state easier (hopefully) 

## How to use it
First you have to bind the properties in your classes using a `ReactiveState` class. 

Heres an example

```java
// before
String name = "bob";

// after
ReactiveState<String> name = new ReactiveState<>("bob");
```

Now you have to add an annotation above your `ReactiveState` which specifies the callback function which will execute once the state is changed.

```java
// before
@StateChange("onChange") // name of callback func
ReactiveState<String> name = new ReactiveState<>("bob");

void onChange() {
    System.out.println("name changed");
}
```

Final step. we have to let the reactive state manager know that the class is ready to initialize the reactive state. we can put this in the constructor.

```java
class ReactiveStateTest {
    @StateChange("onChange")
    ReactiveState<String> name = new ReactiveState<>("bob");
    
    public ReactiveStateTest() {
        // tell it to init reactive state
        Reactive.init(this);
    }

    void onChange() {
        System.out.println("name changed");
    }
}
```

**IMPORTANT NOTE:** in order to avoid nullptr exceptions YOU MUST INITIALIZE EVERY SINGLE VARIABLE which you want to be a reactive state BEFORE you call the `Reactive.init` method. This means you cannot do this:

```java
/*
code which will error :(
*/
class ReactiveStateTest {
    @StateChange("onChange")
    ReactiveState<String> name; // name is null

    public ReactiveStateTest() {
        // tell it to init reactive state
        Reactive.init(this);
    }

    void onChange() {
        System.out.println("name changed");
    }
}
```

Since you are in control as to when you initialize the reactive states, you must make sure that you dont leave any reactive states null before init.

## How to actually set and get the state
In order for the reactive state tracking to work, you cannot just directly access and modify the property directly. instead, `ReactiveState` generates a setter and getter method to use.
```java
// getting the name
String retrievedName = name.get();

// setting the name, will trigger the callback linked to the reactive state
name.set("hello");

// if you want to trigger callback without changing state just leave the params empty
name.set();

// if you want to modify the state but NOT trigger callback, add an extra parameter
name.set("hello", true); // does not trigger callback
```

## Antipatterns
one antipattern to almost NEVER do is to modify a target reactive state from inside of the callback function linked to it. this will cause an infinite feedback loop.

Example of this:

```java
/*
Dont do this
*/
class ReactiveStateTest {
    @StateChange("onChange")
    ReactiveState<String> name = new ReactiveState<>("bob");

    public ReactiveStateTest() {
        Reactive.init(this);
    }

    void onChange() {
        this.name.set("josh"); // will cause infinite feedback loop!!!!
    }
}
```

If you really need to do this (even though you really shouldnt) just specify in the params that you want to skip the callback function execution just for that set action.

```java
class ReactiveStateTest {
    @StateChange("onChange")
    ReactiveState<String> name = new ReactiveState<>("bob");

    public ReactiveStateTest() {
        Reactive.init(this);
    }

    void onChange() {
        this.name.set("josh", true); // will not trigger callback on set -> so will not cause infinite feedback loop.
    }
}
```

## Integrating with dependency injection
you can integrate with the dependency injection framework.

```java
class ReactiveStateTest extends Injectable {
    @Inject("name")
    @StateChange("onChange")
    ReactiveState<String> name;

    public ReactiveStateTest() {
        super();
        Reactive.init(this);
    }

    void onChange() {
        System.out.println("name was updated");
    }
}
```
