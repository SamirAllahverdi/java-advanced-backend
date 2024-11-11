import com.epam.jmp.service.Bank;
import com.epam.jmp.service.service.Service;

module jmp.app {
    uses Service;
    uses Bank;
    requires jmp.cloud.bank.impl;
    requires jmp.cloud.service.impl;
    requires jmp.dto;
}