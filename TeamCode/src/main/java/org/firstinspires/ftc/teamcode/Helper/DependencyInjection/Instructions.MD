Added new Dependency Injection framework which can be used to decouple dependencies without requiring the `EventBus`

Usage:

(example here shown with an ArrayList of strings)

```java
class InjectablesTest extends Injectable {
    // add the @Inject annotation above fields you want to be injected
    @Inject("values")
    List<String> vals = new ArrayList<>();

   public void print() {
       // return if any required deps are null, to avoid nullptr exceptions
        if (areDepsNull(vals)) return;
        
        for (String val : vals) {
            System.out.println(val);
        }
    }
}
```

```java
class Loader {
        public static void load() {
                List<String> vals = new ArrayList<>();
                vals.add("val 1");
                vals.add("val 2");
                vals.add("val 3");
        
                DependencyInjector.register("values", vals);
        }
}
```

```java
class Example extends LinearOpMode {
    void runOpMode() {
        waitForStart();
        Loader.load();
        InjectablesTest test = new InjectablesTest();
        test.print();

        // error handling for dependency injection errors
        if (test.getException() != null) {
            System.out.println(test.getException().toString());
        }

        // Un-registering the cached dependency
        DependencyInjector.unregister("values");
        
        // Cleaning injectable values of a class
        // Sets all injectable values to null, preserves non injectable values
        test.removeInjectables();
    }
}
```

This will print out the values in order (from the ArrayList). The main point here is that nothing was passed from `Example` to `Test` when instantiation occurred. Instead, the `Loader` caches the `ArrayList` of values, and the dependency injectors injects the corresponding dependencies into `Test` as required - this approach is much better at following SOLID principles.  Now, `Test` has no information about how or what created the `ArrayList`, and it does not to  modify or overload its constructor parameters in order to get the dependency. A dedicated class (Like `Loader`) can handle caching dependencies, and these are injected into the needed class.

**IMPORTANT NOTE:** Any classes which have _ALREADY_ been injected with dependencies will RETAIN their dependencies, even after they are unregistered from the `DependencyInjector`. However, any new instances of the class will throw errors (as the cached dependency it is looking for won't exist). Be careful when unregistering dependencies and removing injectables. Always check for nullptr exceptions.
