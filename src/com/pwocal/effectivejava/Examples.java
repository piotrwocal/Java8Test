package com.pwocal.effectivejava;

/**
 * Created by nomad on 03.02.18.
 */
public class Examples {

    // Item 1: Static Factory Method
    //      single point to return object that is named, possibly cached, or subclassed.
    //      If class does not have public constructor, it cannot be instatiated.
    // vs Factory Method:
    //      new types of returned services by subclassing
    // vs Bridge pattern (creational variant)
    //      separates by two inheritance lines abstractions from implementation like:
    //      Abstraction -> Shape > Circle, Implementation -> DrawingAPI > DrawingAPI1 > DrawingAPI2

    // Item 2: Builder
    //      solves problems with long params list for constructor and mutability.
    //      It's well suited for class hierarchies, use parallel hierarchy of builders.
    //      Abstract classes have abstract builders, concrete classes have concrete builders
    //      with builder with generic type with recursive type parameter <T extends Builder<T>>
    //      that along with T self() methods is known as simulated self-type idiom.

    // Item 3: Force singleton with private constructor or enum (prefered)
    //      In case of private constructor if class implements serializable,
    //      one must implements readResolve()

    // Item 4: Ensure non instancablity with private constructor throwing Exception
    


}

