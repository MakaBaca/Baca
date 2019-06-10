package com.mak.baca.card;

public class Cards {
	
	Suit s;
    Rank r;
    public  enum  Suit{
        Spade , Heart , Diamond , Clubs
    }

    public enum Rank{
        ACE(1) , TWO(2), THREE(3), FOUR(4), FIVE(5) , SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN (10), JACK(10), QUEEN (10), KING(10);

        int priority;
        Rank(int s) {
            priority = s;
        }

        public int getPriority(){
            return priority;
        }

        public Rank getRankByPriority(int p){
            switch (p){
                case 1: return Rank.ACE;
                case 2: return Rank.TWO;
                case 3: return Rank.THREE;
                case 4: return Rank.FOUR;
                case 5: return Rank.FIVE;
                case 6: return Rank.SIX;
                case 7: return Rank.SEVEN;
                case 8: return Rank.EIGHT;
                case 9: return Rank.NINE;
                case 10: return Rank.TEN;
                case 11: return Rank.JACK;
                case 12: return Rank.QUEEN;
                case 13: return Rank.KING;

                default: return null;
            }

        }
    }

    Rank getRank(){
        return r;
    }

    Suit getSuit(){
        return s;
    }


    Cards(Rank r, Suit s){
        this.r = r;
        this.s = s;
    }

}

class cardComparator implements java.util.Comparator<Cards.Rank>{

    @Override
    public int compare(Cards.Rank o1, Cards.Rank o2) {
        if(o1.getPriority() > o2.getPriority()){
            return 1;
        }else if(o1.getPriority() < o2.getPriority()){
            return -1;
        }else{
            return 0;
        }
    }

 }
