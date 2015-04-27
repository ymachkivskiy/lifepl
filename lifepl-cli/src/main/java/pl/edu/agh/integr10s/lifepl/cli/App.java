package pl.edu.agh.integr10s.lifepl.cli;

import pl.edu.agh.integr10s.lifepl.model.util.IdempotentFunction;

public class App
{

    private static class A {

        private final int value ;

        public A(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "A{value=" + value + "}";
        }

        @Override
        public int hashCode() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (obj instanceof A) {
                A other = (A) obj;
                return value == other.getValue();
            }
            return false;
        }
    }

    public static void main( String[] args ) {
        IdempotentFunction<A, String> f = new IdempotentFunction<A, String>() {
            @Override
            protected String apply(A argument) {
                return String.valueOf(argument.getValue()) + "blblbas";
            }
        };

        System.out.println(f.calculateFor(new A(10)));
        System.out.println(f.calculateFor(new A(10)));
        System.out.println(f.calculateFor(new A(15)));
    }
}
