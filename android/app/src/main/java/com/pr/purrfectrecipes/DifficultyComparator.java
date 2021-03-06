package com.pr.purrfectrecipes;

import java.util.Comparator;

public class DifficultyComparator implements Comparator
{

    public int compare(Object o1, Object o2)
    {
        Recipe p1=(Recipe)o1;
        Recipe p2=(Recipe)o2;

        if(p1.getRecipeDifficulty().equals("Easy") && p2.getRecipeDifficulty().equals("Medium"))
            return -1;
        else if(p1.getRecipeDifficulty().equals("Easy") && p2.getRecipeDifficulty().equals("Hard"))
            return -1;
        else if(p1.getRecipeDifficulty().equals("Medium") && p2.getRecipeDifficulty().equals("Hard"))
            return -1;
        else if(p1.getRecipeDifficulty().equals(p2.getRecipeDifficulty()))
            return 0;
        else
            return 1;
    }
}