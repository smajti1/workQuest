package it.dziubinski.workInIt

import org.reflections.Reflections
import kotlin.reflect.KClass

fun findImplementations(interfaceClass: KClass<out Any>): List<KClass<*>> {
    val reflections = Reflections("it.dziubinski.workInIt")
    val subTypes = reflections.getSubTypesOf(interfaceClass.java)

    return subTypes.map { it.kotlin }
}