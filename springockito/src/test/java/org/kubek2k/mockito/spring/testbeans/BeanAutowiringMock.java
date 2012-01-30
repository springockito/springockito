package org.kubek2k.mockito.spring.testbeans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BeanAutowiringMock {

    private final InterfaceToBeMocked interfaceToBeMocked;

    @Autowired
    public BeanAutowiringMock(InterfaceToBeMocked interfaceToBeMocked) {
        this.interfaceToBeMocked = interfaceToBeMocked;
    }

    public InterfaceToBeMocked getInterfaceToBeMocked() {
        return interfaceToBeMocked;
    }
    
    public Integer getValue() {
        return interfaceToBeMocked.getValue();
    }
}
