module jmp.cloud.service.impl {
    requires transitive jmp.service.api;
    requires jmp.dto;

    exports com.epam.jmp.service.service.impl;
    exports com.epam.jmp.service.exception;
}
