package com.jaydenxiao.common.commonutils;


import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by yuchao on 2017/4/27.
 */

public class UUIDUtil {

    public static String simpleHex(String separator) {
        return (String) new UUIDHexGenerator(separator).generate();
    }

    public static String simpleHex() {
        return (String) new UUIDHexGenerator().generate();
    }

    static class UUIDHexGenerator {

        private static final int ip;
        static {
            int ipadd;
            try {
                ipadd = toInt( InetAddress.getLocalHost().getAddress() );
            }
            catch (Exception e) {
                ipadd = 0;
                e.printStackTrace();
            }
            ip = ipadd;
        }

        private static short counter = (short) 0;
        private static final int jvm = (int) ( System.currentTimeMillis() >>> 8 );

        private final String sep;

        public UUIDHexGenerator() {
            super();
            sep = "";
        }

        public UUIDHexGenerator(String sep) {
            super();
            this.sep=sep;
        }

        protected String format(int intval) {
            String formatted = Integer.toHexString(intval);
            StringBuffer buf = new StringBuffer("00000000");
            buf.replace( 8-formatted.length(), 8, formatted );
            return buf.toString();
        }

        protected String format(short shortval) {
            String formatted = Integer.toHexString(shortval);
            StringBuffer buf = new StringBuffer("0000");
            buf.replace( 4-formatted.length(), 4, formatted );
            return buf.toString();
        }

        public Serializable generate() {
            return new StringBuffer(36)
                    .append( format( getIP() ) ).append(sep)
                    .append( format( getJVM() ) ).append(sep)
                    .append( format( getHiTime() ) ).append(sep)
                    .append( format( getLoTime() ) ).append(sep)
                    .append( format( getCount() ) )
                    .toString();
        }

        public static void main( String[] args ) throws Exception {
            UUIDHexGenerator gen = new UUIDHexGenerator("-");
            UUIDHexGenerator gen2 = new UUIDHexGenerator("-");
            for ( int i=0; i<10; i++) {
                String id = (String) gen.generate();
                System.out.println("gen1: " +  id + ": " +  id.length() );
                String id2 = (String) gen2.generate();
                System.out.println("gen2: " +  id2 + ": " +  id2.length() );
            }
        }

        /**
         * Unique across JVMs on this machine (unless they load this class
         * in the same quater second - very unlikely)
         */
        private int getJVM() {
            return jvm;
        }

        /**
         * Unique in a millisecond for this JVM instance (unless there
         * are > Short.MAX_VALUE instances created in a millisecond)
         */
        private short getCount() {
            synchronized(UUIDHexGenerator.class) {
                if (counter<0) counter=0;
                return counter++;
            }
        }

        /**
         * Unique in a local network
         */
        private int getIP() {
            return ip;
        }

        /**
         * Unique down to millisecond
         */
        private short getHiTime() {
            return (short) ( System.currentTimeMillis() >>> 32 );
        }

        private int getLoTime() {
            return (int) System.currentTimeMillis();
        }

        private static int toInt( byte[] bytes ) {
            int result = 0;
            for (int i=0; i<4; i++) {
                result = ( result << 8 ) - Byte.MIN_VALUE + (int) bytes[i];
            }
            return result;
        }
    }
}
