package us.timinc.mc.cobblemon.cobbledalphas.event.handlers

abstract class AbstractEventHandler<E> {
    abstract fun handle(e: E)
}