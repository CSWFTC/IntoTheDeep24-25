# EventBus

This is a quick manual which explains how to use the `EventBus` class.

### Accessing EventBus
`EventBus`'s singleton instance can be accessed by using the `.getInstance()` method. In order to use it: `EventBus.getInstance()` - which returns the singleton instance of type `EventBus`.

### Subscribing to the EventBus
In order to subscribe to the `EventBus`, both a unique `subscriptionId` and a class which implements `Subscriber` is needed.

#### Creating the Subscriber implementation
There are two main ways to create a `Subscriber` implementation.
<br /><br />
**Method 1:**
This involves creating a new file and class for the new `Subscriber` implementation. This is much more verbose and should be used for bigger and more complex implementations.
Here is an example implementation:

```java
class SubscriberImplementation extends Subscriber<String, Void> {
    public SubscriberImplementation() {}
    
    @Override
    public String onMessage(Object ... params) {
        // method logic here 
        if (params.length > 0 && params[0] instanceof String) {
           return (String)params[0];
        }
        return "";
    }
    
    @Override
    public Void onMessage() {
        // method logic here
        return null;
    }
}
```
<br />

**Method 2:**

For smaller, less complex implementation, you can use the `SubscriberUtils` class to help generate a `Subscriber` implementation. Here is an example:

```java
Subscriber<String, Void> generated = SubscriberUtils.generateSubscriberClass(
        params -> {
            if (params.length > 0 && params[0] instanceof String) {
                return (String)params[0];
            }
            return "";
        },
        () -> {
            return null;
        }
);
```
The `generateSubscriberClass` method is overloaded, so you can input whichever combination of `onMessage` types that you wish.

#### Creating the Subscription
Creating the subscription is different depending on which method above you used.

If you used method 1:

```java
SubscriberImplementation impl = new SubscriberImplementation();
EventBus.getInstance().subscribe("test", impl.getClass());
```

if you used method 2, you need to explicitly type cast:

```java
EventBus.getInstance().subscribe("test", (Class<? extends Subscriber<?, ?>>) generated.getClass());
```

Great! Now you have created a subscription to the EventBus.

### Emitting events to the EventBus
Emitting events is very straightforwards. You just need to create a `Set` with the different "channels" you are emitting to, and then you optionally pass in any parameters.
```java
Set<String> targets = new HashSet<>();
targets.add("test");
targets.add("hello");
EventBus.getInstance().emit(targets, "Hi");
```
The extra parameter (in this case, the String "Hi") can be used inside `onMessage` methods of `Subscribers` via the overloaded method which includes `Object ... params` as the params.

### Unsubscribing
Just use the `unsubscribe()` method and pass in the unique subscriptionId.

```java
EventBus.getInstance().unsubscribe("test");
```