package blockchain;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Enter how many zeros the hash must starts with: ");
        var scanner = new Scanner(System.in);
        int numZeros = scanner.nextInt();
        scanner.close();

        BlockChain bc = new BlockChain(numZeros, "/Users/will/block.chain");
        for (int i = 0; i < 5; ++i) bc.newBlock();
        System.out.println(bc);
        bc.save();
    }
}
