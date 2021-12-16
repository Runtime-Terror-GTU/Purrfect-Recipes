package com.example.purrfectrecipes;

import java.util.Comparator;

public class DifficultyComparator implements Comparator
{
    /**
     * Compares the product with another product.
     * @return an integer greater than 0 if this.discountPrice>other.discountPrice, smaller than 0 if this.discountPrice<otherdiscountPrice, o if thisdiscountPrice=otherdiscountPrice
     */
    public int compare(Object o1, Object o2)
    {
        Recipe p1=(Recipe)o1;
        Recipe p2=(Recipe)o2;

        if(p1.getRecipeDifficulty()=="Easy" && p2.getRecipeDifficulty()=="Medium")
            return -1;
        else if(p1.getRecipeDifficulty()=="Easy" && p2.getRecipeDifficulty()=="Hard")
            return -1;
        else if(p1.getRecipeDifficulty()=="Medium" && p2.getRecipeDifficulty()=="Hard")
            return -1;
        else if(p1.getRecipeDifficulty()==p2.getRecipeDifficulty())
            return 0;
        else
            return 1;
    }
}