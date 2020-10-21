package com.example.testcloudfirebase;

public class LongestCommonSubstring {


            /* Returns length of LCS for X[0..m-1], Y[0..n-1] */
            public int lcs ( char[] X, char[] Y, int m, int n )
            {

                if (m == 0 || n == 0)
                    return 0;
                if (Character.toLowerCase(X[m - 1])== Character.toLowerCase(Y[n - 1]))
                    return 1 + lcs(X, Y, m - 1, n - 1);
                else
                    return max(lcs(X, Y, m, n - 1), lcs(X, Y, m - 1, n));
            }

    public int lcsByString (String s1, String s2)
            {
                char[] X=s1.toCharArray();
                char[] Y=s2.toCharArray();
                if(s1.length()>s2.length())
                    return lcs(X,Y,s2.length(),s2.length());
                else
                    return lcs(X,Y,s1.length(),s2.length());
            }

            /* Utility function to get max of 2 integers */
            int max ( int a, int b)
            {
                return (a > b) ? a : b;
            }


    public  boolean isDouble(String s)
            {

                char[] str=s.toCharArray();
                for(char ch : str)
                {
                    if (!((ch>='0'&&ch<='9') || ch=='.' || ch==',' ))
                    {
                        return false;
                    }
                }

                return true;
            }

    public   double returnDouble(String s)
            {
                char[] str=s.toCharArray();
                for(int i=0 ;i<str.length-1;i++)
                {
                    if(str[i]==',')
                        str[i]='.';
                }

                if(str[str.length-1]=='g' ||str[str.length-1]=='q')
                    return Double.parseDouble(String.valueOf(str,0,str.length-1));
                else if(str[str.length-1]=='9')
                    return Double.parseDouble(String.valueOf(str,0,str.length-1));

                return Double.parseDouble(new String(str));
            }
        }