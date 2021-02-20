package de.fallapalooza.streamapi.annotation.processor;

/**
 * A compiler which turns a class into a CellDefinition in some way
 */
public interface CellDefinitionCompiler {
    <T> CellDefinition<T> compile(Class<T> clazz);
}
