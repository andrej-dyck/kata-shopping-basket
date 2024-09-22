# Shopping Basket Kata

![build](https://github.com/andrej-dyck/kata-shopping-basket/actions/workflows/ci.yml/badge.svg?branch=main)

A coding kata that shows an implementation for a shopping basket in the context of an e-commerce webshop with the focus on modelling the discount logic and unit tests showing the behaviour.

## Run Checks

Requires Java JDK to be installed.  
```bash
./gradlew build
```

## Remarks

This solution emphasizes functional programming principles, focusing on reducing complexity by clearly separating effects from core logic. At the heart of this approach are two key concepts: immutable data (values, not mutable state) and pure functions (deterministic expressions, not statements). By adhering to these, the design pushes side effects, validation, and state management to the system's boundaries—such as during user actions or system events.

In general, separating (side-)effects from logic reduces complexity and cognitive load. However, the readability and maintainability of a codebase largely depend on the team's familiarity with these paradigms (and a good safetynet comprised of automated tests ;)). While I find functional programming effective for this problem and am comfortable with it (both writing and teaching), I recognize that the style and technology choice should be based on the project context, the team's expertise, and how willing future maintainers are to adapt or learn.

Additionally, I leverage the type system to prevent mistakes by minimizing illegal values and state. This reduces the need for excessive testing and allows the compiler to serve as the first line of feedback for developers.

To see how the solution evolves, follow the commit history.

## Design Decisions

**Commit 'setup kotlin project'**
- *[Kotlin](https://kotlinlang.org/) has a robust type system, concise syntax, and an extensive standard library*
- *[detekt](https://detekt.dev/) is used as a linter; configured with stricter rules for code quality*

**Commit 'define basket with items'**
- *Basket maintains item insertion order*
    - Users typically expect the most recent item to appear at the bottom (or top) of the list
- *No duplicate items per product*
    - When adding the same product (identified by SKU) in a webshop, the quantity increases rather than listing the item multiple times
- *Basket and its items are immutable data*
    - Although immutable structures have some memory overhead, it’s negligible for small datasets
    - The benefits of thread safety and simpler logic outweigh these drawbacks
- *Products are identified by SKU*
- *No check against a warehouse / inventory stock*
    - Since this kata focuses on price calculation, inventory validation is omitted
    - However, stock checks can be added later near user actions or during post-basket validation
- *`Quantity` and `Sku` types are [inline value classes](https://kotlinlang.org/docs/inline-classes.html)*:
    - Inline classes allow us to create domain-specific types with no runtime overhead while enforcing logic at compile-time

**Commit 'define price list for products'**
- *Basket keeps only track of SKU and amount*
    - This separates the logic for "what's in the basket" from "how much the items cost"
- *Prices are regulated by sales*
    - Typically, sales is in charge of pricing products (not the warehouse or inventory)
    - A real-world implementation would fetch prices via an API or service
    - In this kata, prices are assumed to be provided in Euros
- *Monetary values (`Euros`) are integer numbers*
    - Using integers avoids the pitfalls of floating-point arithmetic, such as imprecision in calculations; e.g., `0.1 + 0.2 == 0.30000000000000004 (!= 0.3)` or `1000000.1 - 1000000.0 == 0.09999999997671694 (!= 0.1)`
    - Further, computers are optimized for integer calculations
    - Inline value classes are again used for efficiency
    - While BigDecimal could be an alternative for precision, it's interface is cumbersome to use (it needs numbers as string a string to be precise) and inconvenient for a multiplatform project

**Commit 'define basket total based on price list'**
- *Basket `total()` performs price lookups*
    - Prices can be fetched when the user views the basket, allowing flexibility to account for price changes, timed deals, or currency adjustments between "added to basket" and "order now"
- *`PriceList` is not an API call*
  - To keep `total()` a pure function, asynchronous code (such as API calls) mustn't be injected
  - The `PriceList` is constructed outside of this function at the edge of the system next to a user or system event
- *Missing prices raise an error*
    - We expect that sales has a price for all items in a basket
    - Alternative solutions could include returning explicit error types or removing items from the basket 

**Commits 'draft ... deal'**
- *Deals offer discounts taking all (priced) items into account*
  - In pseudocode: `(items: { sku, quantity, price }[]) -> discount?`
  - While not needed for the implemented example deals, their implementation took only a few minutes of thoughts more and this signature keeps our options open to extend the code without changing it
  - For example, signature allows to add deals such as "discount for a combination of two specific items", "bonus item when total exceeds certain value", "general discount", and a combination of deals
- *Discount is nullable*
  - Kotlin's nullable types (similar to Maybe/Optional) simplify deal with no discounts for now
  - This could evolve into a more structured type for handling various discount scenarios

**Commit 'apply deals on basket total'**
- *Basket `total()` applies eligible discounts*
  - Similar to price lookups, discounts are applied lazily when calculating the total, ensuring separation of concerns between calculations and data
- *Basket `total()` supports multiple active deals*
  - The system accounts for multiple active discounts when calculating the total
- *`Deal` is not intended to be an API call*
  - Like the price list, active deals are determined at the edges of the system, keeping the core logic pure
- *Basket `total()` returns a single value in Euros*
  - Although this is sufficient for the kata, future designs may include a detailed breakdown of the total, pre-discount total, and discounts applied

## Metrics (based on detekt)

**In production code**

- Lines of code: 120
- Lines of logic code: 83


- Number of functions: 21
- ... of which are trivial functions: 10
- Number of test cases: 31


- Number of code smells: 0
- Cognitive Complexity: 1
- McCabe Cyclomatic Complexity: 1.24 per function (average)


- Number of classes: 5
- Number of interfaces: 1
- Number of data classes (records): 3
- Number of inline classes (wrapped primitives): 4
