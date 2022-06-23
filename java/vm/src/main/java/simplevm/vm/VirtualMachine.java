package simplevm.vm;

import jdk.jshell.spi.ExecutionControl;

import java.util.Arrays;

public class VirtualMachine {
    private static int maxSize = 100;
    private final int stack[];
    private int sp = -1;

    public VirtualMachine() {
        stack = new int[maxSize];
    }

    public int[] getStack() {
        if(sp == -1)
            return new int[0];

        int[] out = new int[sp+1 ];
        for(int i = 0; i <= sp; i++) {
            out[i] = stack[i];
        }

        return out;
    }

    public void push(int v) {
        sp++;
        stack[sp] = v;
    }

    public int pop() {
        //todo if -1 => fatal exception

        int v = stack[sp];

        sp--;
        if(sp < 0)
            sp = -1;

        return v;
    }

    public static class Exception extends java.lang.RuntimeException {
        public Exception(String message) {
            super(message);
        }
    }


    // Tracing
    //
    boolean trace = false;
    private void trace(String message) {
        if (trace) {
            System.out.println(/*"(IP " + ip + "):" +*/ message);
        }
    }

    // Dump
    //
    private void dump() {
        System.out.println("SimpleVM DUMP");
        System.out.println("=============");

//        System.out.println("IP: " + ip);
//        System.out.println("Working stack (SP " + sp + "): " + Arrays.toString(Arrays.copyOfRange(stack, 0, sp+1)));
//        System.out.println("Globals: " + Arrays.toString(globals));
//        System.out.println("Call stack: ");
//        for (int f = frames.size(); f != 0; f--) {
//            CallFrame cf = frames.get(f - 1);
//            System.out.println("  Call Frame " + (f - 1) + ":");
//            System.out.println("  +-- Return Address: " + cf.returnAddress);
//            System.out.println("  +-- Locals: " + Arrays.toString(cf.locals));
//        }

    }

    public void execute(int opcode, int... operands) {
        try {
            trace(String.format("op code: %s, op codes: %s",
                    Bytecode.getOpCodeName(opcode), Arrays.toString(operands)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        switch (opcode){
            case Bytecode.CONST:
                push(operands[0]);
                break;
            case Bytecode.POP:
                pop();
                break;
            case Bytecode.NOP:
                break;
            case Bytecode.DUMP:
                dump();
                break;
            case Bytecode.PRINT:
                print();
                break;
            case Bytecode.TRACE:
                trace = !trace;
                break;
            default:
                throw new IllegalArgumentException("Not implemented: " +opcode);
        }

    }

    private void print() {
        System.out.println("value: " + pop());
    }

    public void execute(int[] code) {
        int size= code.length;
        int i = 0;

        int opcode;
        while (i < size) {
            opcode = code[i++];

            int requiredOp = requiredOperands(opcode);
            int[] opCodeOperands = new int[requiredOp];
            for(int j = 0; j<requiredOp; j++) {
                opCodeOperands[j] = code[i + j];
            }
            execute(opcode, opCodeOperands);

            i += requiredOp;
        }
    }

    private int requiredOperands(int opcode) {
        switch (opcode) {
            case Bytecode.CONST:
                return 1;
            case Bytecode.POP:
            case Bytecode.NOP:
            case Bytecode.DUMP:
            case Bytecode.PRINT:
            case Bytecode.TRACE:
                return 0;
            default:
                    throw new IllegalArgumentException("Not implemented: " +opcode);
        }
    }

}
