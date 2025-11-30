# JAVA REFERENCE - OOP CONCEPTS NOTEBOOK

**A collection of Java OOP concepts explained for Patient Zero project**

---

## TABLE OF CONTENTS
1. [Interfaces vs Abstract Classes vs Regular Classes](#interfaces-vs-abstract-classes-vs-regular-classes)
2. [Inheritance Explained](#inheritance-explained)
3. [Polymorphism Explained](#polymorphism-explained)
4. [Abstract vs Concrete Methods](#abstract-vs-concrete-methods)
5. [Constructor vs Instantiation](#constructor-vs-instantiation)
6. [Why Interfaces and Abstract Classes Can't Be Instantiated](#why-interfaces-and-abstract-classes-cant-be-instantiated)

---

## Interfaces vs Abstract Classes vs Regular Classes

### Quick Comparison Table

| Feature | Interface | Abstract Class | Regular Class |
|---------|-----------|----------------|---------------|
| **Abstract methods?** | ✅ All methods | ✅ Can have some | ❌ None |
| **Concrete methods?** | ❌ No* | ✅ Can have some | ✅ All methods |
| **Fields?** | ❌ Only constants | ✅ Yes | ✅ Yes |
| **Constructor?** | ❌ No | ✅ Yes (for subclasses) | ✅ Yes |
| **Can instantiate?** | ❌ No | ❌ No | ✅ Yes |
| **Multiple inheritance?** | ✅ Can implement many | ❌ Can only extend one | ❌ Can only extend one |

*Interfaces can have default methods in modern Java

---

### Interface

**Definition:** A contract that defines what methods a class must implement, but not how.

```java
// effect/Effect.java
package effect;
import model.Player;

public interface Effect {
    // All methods are automatically abstract
    void apply(Player player);
    String getDescription();
    // No fields (except constants)
    // No constructor
    // No implementation
}
```

**Usage:**
```java
// Implementing class provides the implementation
public class HealthEffect implements Effect {
    private int magnitude;
    
    public HealthEffect(int magnitude) {
        this.magnitude = magnitude;
    }
    
    @Override
    public void apply(Player player) {
        player.changeHealth(magnitude);
    }
    
    @Override
    public String getDescription() {
        return (magnitude > 0 ? "+" : "") + magnitude + " Health";
    }
}

// ❌ Cannot do:
Effect e = new Effect();  // ERROR! Interface cannot be instantiated

// ✅ Can do:
Effect e = new HealthEffect(5);  // Concrete class implementing interface
```

**When to use:**
- Defining a contract ("what" something can do)
- Need multiple inheritance
- No shared code between implementations

---

### Abstract Class

**Definition:** A hybrid between interface and regular class - can have both abstract methods (no implementation) and concrete methods (with implementation).

```java
// Abstract class example
public abstract class Vehicle {
    // ✅ Can have fields
    protected String brand;
    protected int speed;
    
    // ✅ Can have constructor (for subclasses)
    public Vehicle(String brand) {
        this.brand = brand;
        this.speed = 0;
        System.out.println("Vehicle: " + brand + " is being built");
    }
    
    // ✅ Concrete method (has implementation)
    public void accelerate(int amount) {
        speed += amount;
        System.out.println(brand + " speed: " + speed);
    }
    
    // ❌ Abstract method (no implementation)
    public abstract void honk();
}
```

**Usage:**
```java
public class Car extends Vehicle {
    private int doors;
    
    public Car(String brand, int doors) {
        super(brand);  // Calls parent constructor
        this.doors = doors;
    }
    
    @Override
    public void honk() {
        System.out.println(brand + ": Beep beep!");
    }
}

// ❌ Cannot do:
Vehicle v = new Vehicle("Toyota");  // ERROR! Abstract class cannot be instantiated

// ✅ Can do:
Vehicle car = new Car("Toyota", 4);  // Concrete subclass
car.accelerate(50);  // Uses inherited concrete method
car.honk();          // Uses Car's implementation
```

**When to use:**
- Have shared code between subclasses
- Want to provide default implementations
- Need to enforce some methods while providing others

---

### Regular Class

**Definition:** A complete, concrete class with all methods implemented.

```java
public class Player {
    private int health;
    private int energy;
    
    // Constructor
    public Player(int health, int energy) {
        this.health = health;
        this.energy = energy;
    }
    
    // All methods are concrete
    public void changeHealth(int delta) {
        this.health += delta;
    }
    
    public int getHealth() {
        return health;
    }
}

// ✅ Can instantiate
Player p = new Player(10, 3);
```

---

## Inheritance Explained

**Inheritance = "IS-A" relationship**

When a class extends another class OR implements an interface, that's inheritance!

### Two Types of Inheritance

#### Type 1: Class Inheritance (Parent-Child)

```java
class Animal {  // Parent/Superclass
    protected String name;
    
    public void eat() {
        System.out.println("Eating...");
    }
}

class Dog extends Animal {  // Child/Subclass
    public void bark() {
        System.out.println("Woof!");
    }
}

// Dog "IS-A" Animal ✅
// Dog has name field (inherited)
// Dog has eat() method (inherited)
```

#### Type 2: Interface Inheritance

```java
interface Effect {
    void apply(Player player);
}

class HealthEffect implements Effect {
    public void apply(Player player) {
        player.changeHealth(5);
    }
}

// HealthEffect "IS-A" Effect ✅
// This IS inheritance!
```

**Both count as inheritance for your assignment!** ✅

---

## Polymorphism Explained

**Polymorphism = "Many Forms"**

One interface, multiple implementations. The same method name behaves differently based on the object type.

### Type 1: Method Overriding (Runtime Polymorphism)

**The main type you'll use:**

```java
interface Effect {
    void apply(Player player);
}

class HealthEffect implements Effect {
    @Override
    public void apply(Player player) {
        player.changeHealth(5);
    }
}

class EnergyEffect implements Effect {
    @Override
    public void apply(Player player) {
        player.changeEnergy(2);
    }
}

// Polymorphism in action:
Effect effect1 = new HealthEffect();
Effect effect2 = new EnergyEffect();

effect1.apply(player);  // Calls HealthEffect's apply()
effect2.apply(player);  // Calls EnergyEffect's apply()

// Same method name, different behavior!
```

**Example with List:**
```java
List<Effect> effects = new ArrayList<>();
effects.add(new HealthEffect(5));
effects.add(new EnergyEffect(2));
effects.add(new KnowledgeEffect(1));

// ✅ POLYMORPHISM: Each calls its own implementation
for (Effect e : effects) {
    e.apply(player);  // Different behavior for each effect type!
}
```

### Type 2: Method Overloading (Compile-time Polymorphism)

**Less common, but still polymorphism:**

```java
class Player {
    // Same method name, different parameters
    void changeHealth(int delta) {
        health += delta;
    }
    
    void changeHealth(int delta, String reason) {
        health += delta;
        System.out.println("Health changed: " + reason);
    }
}
```

---

## Abstract vs Concrete Methods

### Abstract Method

**"Tells WHAT to do, but not HOW"**

```java
// Just a signature, no body (no implementation)
public abstract void apply(Player player);  // ← Abstract

// Ends with semicolon ;
// Subclasses MUST provide the implementation
```

**Example:**
```java
abstract class Animal {
    // Abstract method - just declares it exists
    public abstract void makeSound();
}

class Dog extends Animal {
    // Must implement abstract method
    @Override
    public void makeSound() {
        System.out.println("Bark!");  // NOW it's concrete
    }
}
```

---

### Concrete Method

**"Tells WHAT to do AND HOW to do it"**

```java
// Has a body with implementation
public void eat() {  // ← Concrete
    System.out.println("Eating...");
    energy += 10;
}

// Has curly braces { }
// Contains actual code
```

**Example:**
```java
abstract class Animal {
    // CONCRETE method - provides full implementation
    public void eat() {
        System.out.println("Nom nom");
        energy += 10;
    }
    
    // ABSTRACT method - just declares it exists
    public abstract void makeSound();
}

class Dog extends Animal {
    // Inherited eat() as-is (concrete from parent)
    
    // Must implement abstract method
    @Override
    public void makeSound() {
        System.out.println("Bark!");
    }
}
```

---

## Constructor vs Instantiation

### Constructor

**A special method that initializes an object when it's created.**

**Characteristics:**
- Has the same name as the class
- No return type (not even void)
- Runs automatically when you use `new`
- Sets up initial values for fields

```java
public class Player {
    private int health;
    private int energy;
    
    // ✅ This is a CONSTRUCTOR
    public Player(int health, int energy) {
        this.health = health;
        this.energy = energy;
    }
}
```

---

### Instantiation

**The process/act of creating an object using the `new` keyword.**

```java
// ✅ This line is INSTANTIATION
Player player = new Player(10, 3);
//              ^^^^ This is instantiation
//                   ^^^^^^ This calls the constructor
```

**What happens during instantiation:**
1. JVM allocates memory for the object
2. Constructor runs to initialize the object
3. Reference is returned and stored in variable

---

### The Relationship

**Think of it like building a house:**

- **Instantiation** = Building a house (the action)
- **Constructor** = The blueprint instructions that run while building (the method)

```java
public class Car {
    private String model;
    private int speed;
    
    // ⚙️ CONSTRUCTOR - the initialization instructions
    public Car(String model) {
        this.model = model;
        this.speed = 0;
        System.out.println("Car is being built...");
    }
}

// 🏭 INSTANTIATION - the act of creating the object
Car myCar = new Car("Tesla");
//          ^^^^^^^^^^^^^^^^^ This is instantiation
//              ^^^^^^^^^^^^^ This calls the constructor
```

**Output:**
```
Car is being built...
```

---

### How This Relates to Class Types

| Type | Has Constructor? | Can Instantiate? | Example |
|------|-----------------|------------------|---------|
| **Regular Class** | ✅ Yes | ✅ Yes | `Player p = new Player(10);` |
| **Interface** | ❌ No | ❌ No | Cannot do `new Effect()` |
| **Abstract Class** | ✅ Yes (for subclasses) | ❌ No | Cannot do `new Animal()` |
| **Concrete Subclass** | ✅ Yes | ✅ Yes | `Animal d = new Dog();` |

---

## Why Interfaces and Abstract Classes Can't Be Instantiated

### Why Interfaces Can't Be Instantiated

**Reason:** An interface has **no implementation**. It's like a blueprint with no building materials.

```java
interface Effect {
    void apply(Player player);  // No implementation!
}

// ❌ CANNOT DO THIS:
Effect e = new Effect();  // ERROR! What would apply() do?

// The compiler asks: "Which apply() code should I run?"
// There IS no code! Just a contract.
```

**You need a concrete class to fill in the details:**

```java
// ✅ MUST DO THIS:
Effect e = new HealthEffect(5);  // HealthEffect provides the implementation
e.apply(player);  // Now there's actual code to run!
```

---

### Why Abstract Classes Can't Be Instantiated

**Reason:** Even though abstract classes can have concrete methods, they're **still incomplete** because of abstract methods.

```java
abstract class Animal {
    protected String name;
    
    // ✅ This is fully implemented (concrete)
    public void eat() {
        System.out.println(name + " is eating");
    }
    
    // ❌ This is NOT implemented (abstract)
    public abstract void makeSound();
}

// ❌ CANNOT DO THIS:
Animal a = new Animal();  // ERROR!
a.makeSound();  // What would this do? There's no code!

// The compiler says: "makeSound() has no implementation!"
```

**Even if 99% of the class is implemented, if even ONE method is abstract, the class is incomplete.**

---

### But Abstract Classes DO Have Constructors

**Why?** For subclasses to use when initializing inherited fields!

```java
abstract class Animal {
    protected String name;  // Field that subclasses inherit
    protected int age;
    
    // ✅ Constructor - child classes NEED this
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
        System.out.println("Animal constructor: Setting up " + name);
    }
    
    public abstract void makeSound();
}

class Dog extends Animal {
    private String breed;
    
    public Dog(String name, int age, String breed) {
        super(name, age);  // ✅ Calls Animal's constructor
        this.breed = breed;
    }
    
    @Override
    public void makeSound() {
        System.out.println(name + " says: Woof!");
    }
}

// Usage:
Animal myDog = new Dog("Rex", 3, "Labrador");
//              ^^^^^^^^^^^^^^^^^^^^^^^^^^^^ Instantiation (Dog is concrete)
//              Calls Dog constructor
//              which calls super() → Animal constructor
```

**Output:**
```
Animal constructor: Setting up Rex
```

**The Constructor Chain:**
```
new Dog("Rex", 3, "Labrador")
    ↓
Dog constructor runs
    ↓
super(name, age) calls Animal constructor
    ↓
Animal constructor initializes name and age
    ↓
Back to Dog constructor to initialize breed
    ↓
Object fully created!
```

---

### Mental Model: Building a House

Think of a class as a recipe book:

| Type | Constructor | Instantiation | Analogy |
|------|------------|---------------|---------|
| **Interface** | ❌ No blueprint details | ❌ Can't build | Just a list: "Must have door, windows" |
| **Abstract Class** | ✅ Partial blueprint | ❌ Can't build (incomplete) | Blueprint with "Add roof (your choice)" |
| **Concrete Class** | ✅ Complete blueprint | ✅ Can build | Full blueprint with all details |

**The constructor is the building instructions.**  
**Instantiation is the act of actually building the house.**

---

### Summary: Abstract Classes

**Abstract classes by themselves can't be instantiated because they may contain abstract methods (like an interface), but they have a constructor so that child classes can inherit the constructor (like regular classes).**

**Abstract Class = Hybrid Design:**
- **Cannot instantiate**: Because they're incomplete (abstract methods, like interfaces)
- **Have constructors**: To initialize inherited fields for subclasses (like regular classes)
- **Hybrid nature**: Combines the "contract" nature of interfaces with the "shared code" nature of regular classes

```
Interface
    ↓
    "Pure contract"
    - No fields
    - No constructor
    - All methods abstract
    - Cannot instantiate
    
Abstract Class
    ↓
    "Partial implementation"
    - Has fields ✅
    - Has constructor ✅ (for subclasses)
    - Some methods concrete, some abstract
    - Cannot instantiate ❌ (incomplete)
    
Concrete Class
    ↓
    "Full implementation"
    - Has fields ✅
    - Has constructor ✅ (for itself)
    - All methods concrete
    - Can instantiate ✅ (complete)
```

---

## Quick Reference Cheat Sheet

### Inheritance
```java
// ✅ Both are inheritance:
class Dog extends Animal { }           // Class inheritance
class HealthEffect implements Effect { } // Interface inheritance
```

### Polymorphism
```java
// ✅ Polymorphism in action:
Effect e = new HealthEffect(5);  // Supertype reference
e.apply(player);  // Calls HealthEffect's apply() - dynamic dispatch!

// Works with interfaces AND abstract classes!
```

### When to Use What
```java
// Use Interface when:
interface Flyable {
    void fly();  // Just a contract
}

// Use Abstract Class when:
abstract class Bird {
    protected int wingspan;  // Shared field
    
    public void chirp() {    // Shared method
        System.out.println("Tweet!");
    }
    
    public abstract void fly();  // Must be implemented
}

// Use Regular Class when:
class Sparrow extends Bird {
    // Complete implementation
    public void fly() {
        System.out.println("Flying!");
    }
}
```

---

**Last Updated:** November 27, 2025  
**For Project:** Patient Zero - CS 2103
