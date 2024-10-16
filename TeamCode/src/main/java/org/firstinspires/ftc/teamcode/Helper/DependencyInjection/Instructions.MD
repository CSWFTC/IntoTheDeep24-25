Added new Dependency Injection framework which can be used to decouple dependencies without requiring the `EventBus`

Usage:

(example here shown with an ArrayList of strings)

```java
class Test {
    // add the @Inject annotation above fields you want to be injected
    @Inject("values")
    List<String> vals = new ArrayList<>();

    public Test() throws Exception {
        // use the inject method to populate fields with cached dependencies
        DependencyInjector.inject(this);
    }

   public void print() {
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
        try {
           Test test = new Test();
           test.print();
        } catch(Exception e) {}
    }
}
```

This will print out the values in order (from the ArrayList). The main point here is that nothing was passed from `Example` to `Test` when instantiation occurred. Instead, the `Loader` caches the `ArrayList` of values, and the dependency injectors injects the corresponding dependencies into `Test` as required - this approach is much better at following SOLID principles.  Now, `Test` has no information about how or what created the `ArrayList`, and it does not to  modify or overload its constructor parameters in order to get the dependency. A dedicated class (Like `Loader`) can handle caching dependencies, and these are injected into the needed class.