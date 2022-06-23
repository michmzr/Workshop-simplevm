package simplevm.vm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static simplevm.vm.Bytecode.*;

class SimpleTests {
    @Test void testTest() {
        assertEquals(2, 1 + 1);
        //assertEquals(2, 1 - 1); // just to fail-test, just in case
    }
    @Test void testInstantiation() {
        VirtualMachine vm = new VirtualMachine();

        assertTrue(vm != null);
    }
    @Test void testNOP() {
        VirtualMachine vm = new VirtualMachine();

        vm.execute(NOP);

        // If we got here, with no exception, we're good
        assertTrue(vm != null);
    }
    @Test void testNOPs() {
        VirtualMachine vm = new VirtualMachine();

        vm.execute(new int[] {
            NOP,
            NOP,
            NOP,
            NOP
        });

        // If we got here, with no exception, we're good
        assertTrue(vm != null);
    }
    @Test void testDump() {
        VirtualMachine vm = new VirtualMachine();

        vm.execute(DUMP);

        // If we got here, with no exception, we're good
        assertTrue(vm != null);
    }
    @Test void testTrace() {
        VirtualMachine vm = new VirtualMachine();

        vm.execute(new int[] {
            TRACE,
            CONST, 130,
            NOP,
            DUMP
        });

        // If we got here, with no exception, we're good
        assertTrue(vm != null);
    }
    @Test void testPrint() {
        VirtualMachine vm = new VirtualMachine();

        vm.execute(new int[] {
            CONST, 12,
            PRINT
        });

        // Check the output for the printed output
        assertEquals(vm.getStack().length, 0);

        // If we got here, with no exception, we're good
        assertTrue(vm != null);
    }
    @Test void testMoreDump() {
        VirtualMachine vm = new VirtualMachine();

        int[] code = {
            Bytecode.CONST, 43,
            Bytecode.NOP,
            Bytecode.DUMP
        };
        vm.execute(code);

        // If we got here, with no exception, we're good
        assertTrue(true);
    }
}
