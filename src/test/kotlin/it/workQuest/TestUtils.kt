package it.workQuest

import org.reflections.Reflections
import kotlin.reflect.KClass

fun findImplementations(interfaceClass: KClass<out Any>): List<KClass<*>> {
    val reflections = Reflections("it.workQuest")
    val subTypes = reflections.getSubTypesOf(interfaceClass.java)

    return subTypes.map { it.kotlin }
}

fun getReflections(): Reflections = Reflections("it.workQuest")
