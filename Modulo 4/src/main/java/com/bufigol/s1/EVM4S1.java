package com.bufigol.s1;

import java.text.DecimalFormat;

public class EVM4S1 {
    public static void main(String[] args) {
        int[] valores={299990,234540,159990,345100};
        int total=0;
        for(int i=0;i<valores.length;i++){
            total+=valores[i];
        }
        System.out.println("El total de su compra es: "+total);
        System.out.println("El IVA es: "+(total*0.19));
        System.out.println("El total de su compra con IVA incluido es: "+(total*1.19));
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("El total de su compra en dolares es: "+df.format(total/916.08));

    }
}
