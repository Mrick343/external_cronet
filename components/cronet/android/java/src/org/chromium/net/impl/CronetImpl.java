package org.chromium.net.impl;

/**
 * Retrieves the implementation backing the currently running Cronet code.
 */
public interface CronetImpl {
    CronetSource getCronetSource();
}
