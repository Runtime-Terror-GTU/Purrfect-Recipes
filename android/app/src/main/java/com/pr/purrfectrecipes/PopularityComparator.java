package com.pr.purrfectrecipes;

import java.util.Comparator;

public class PopularityComparator implements Comparator
{
    public int compare(Object o1, Object o2)
    {
        Recipe p1=(Recipe)o1;
        Recipe p2=(Recipe)o2;

        if(p1.getRecipeLikes()>p2.getRecipeLikes())
            return 1;
        else if(p1.getRecipeLikes()==p2.getRecipeLikes())
            return 0;
        else return -1;

    }
}