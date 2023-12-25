package src.udp;

import java.io.Serializable;

/**
 * The enum Server command type.
 */
public enum ServerCommandType implements Serializable {
    /**
     * Info server command type.
     */
    INFO,
    /**
     * Show server command type.
     */
    SHOW,
    /**
     * Insert server command type.
     */
    INSERT,
    /**
     * Update server command type.
     */
    UPDATE,
    /**
     * Remove key server command type.
     */
    REMOVE_KEY,
    /**
     * Clear server command type.
     */
    CLEAR,
    /**
     * Remove lower server command type.
     */
    REMOVE_LOWER,
    /**
     * Replace if lowe server command type.
     */
    REPLACE_IF_LOWE,
    /**
     * Remove lower key server command type.
     */
    REMOVE_LOWER_KEY,
    /**
     * Min by name server command type.
     */
    MIN_BY_NAME,
    /**
     * Group counting by price server command type.
     */
    GROUP_COUNTING_BY_PRICE,
    /**
     * Print unique manufacture cost server command type.
     */
    PRINT_UNIQUE_MANUFACTURE_COST,
    /**
     * Get server command type.
     */
    GET,
    /**
     * Get by key server command type.
     */
    GET_BY_KEY,
    /**
     * Get unique id server command type.
     */
    GET_UNIQUE_ID,
    /**
     * Error server command type.
     */
    ERROR,
    /**
     * Actualize server command type.
     */
    ACTUALIZE,
    /**
     * Auth server command type.
     */
    AUTH,
    /**
     * Register server command type.
     */
    REGISTER;

}
