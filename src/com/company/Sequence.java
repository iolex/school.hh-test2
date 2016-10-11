package com.company;
import java.math.BigInteger;

/**
 * Created by iolex on 10.10.2016.
 */
public class Sequence {

    private String source;
    public Sequence(String source) {
        this.source = source;
    }

    public String calculate() {
        BigInteger result = BigInteger.valueOf(0);

        BigInteger position = null;
        boolean found = false;

        int sequenceLength = this.source.length();
        for (int i = 1; i <= sequenceLength; i++) {
            //System.out.println("Rank (" + i + ")");
            for (int j = i; j >= 1; j--) {
                //System.out.println(" >> donor numbers: " + j + "");

                // speed hack:
                if (j < sequenceLength) {
                    char nextNumber = this.source.charAt(j);
                    if (nextNumber == '0') {
                        continue;
                    }
                }

                String subSequenceEnd = this.source.substring(0, j);
                BigInteger subSequenceEndNumber = new BigInteger(subSequenceEnd);

                BigInteger subSequenceEndContinueNumber = subSequenceEndNumber.add(BigInteger.valueOf(1));
                String subSequenceEndContinue = subSequenceEndContinueNumber.toString();
                boolean sameRank = subSequenceEndContinue.length() == subSequenceEnd.length();
                if (!sameRank) {
                    subSequenceEndContinue = subSequenceEndContinue.substring(1, subSequenceEndContinue.length());
                }

                String sequenceSearchPart1 = this.source.substring(j, (j + i) < this.source.length() ? (j + i) : this.source.length());
                String sequenceSearchPart2 = this.source.substring(j, (j + i + 1) < this.source.length() ? (j + i + 1) : this.source.length());

                /*System.out.println("[" + this.source + "]");
                System.out.println(
                        "_seqEnd: " + new String(new char[i - j]).replace("\0", "x") + subSequenceEnd
                                + " => "
                                + new String(new char[i - j]).replace("\0", "x") + subSequenceEndContinue
                                + ";");
                System.out.println("_search1: " + sequenceSearchPart1 + ";");
                System.out.println("_search2: " + sequenceSearchPart2 + ";");*/



                String subSequenceStart1 = sequenceSearchPart1.substring(0, (i - j) < sequenceSearchPart1.length() ? (i - j) : sequenceSearchPart1.length());
                String subSequenceSearchPart1End = sequenceSearchPart1.substring((i - j) < sequenceSearchPart1.length() ? (i - j) : sequenceSearchPart1.length());

                int subSequenceStart1Length = subSequenceStart1.length();
                BigInteger subSequenceStart1Number;
                BigInteger subSequenceStart1NumberBefore;
                String subSequenceStart1Before = "";
                int subSequenceStart1LengthBefore = 0;
                boolean needSearch1 = true;
                if (subSequenceStart1Length > 0) {
                    subSequenceStart1Number = new BigInteger(subSequenceStart1);
                    subSequenceStart1NumberBefore = subSequenceStart1Number.subtract(BigInteger.valueOf(1));
                    subSequenceStart1Before = subSequenceStart1NumberBefore.toString();
                    subSequenceStart1LengthBefore = subSequenceStart1Before.length();
                }
                if (!sameRank && subSequenceStart1Length != subSequenceStart1LengthBefore) {
                    needSearch1 = false;
                }

                if (needSearch1) {
                    //System.out.println("...search~1: '" + subSequenceSearchPart1End + "' in '" + subSequenceEndContinue + "'");
                    int search1 = subSequenceEndContinue.indexOf(subSequenceSearchPart1End);
                    if (subSequenceSearchPart1End.length() == 0 || search1 == 0) {
                        String checkNumber = "";
                        if (sameRank) {
                            checkNumber = subSequenceStart1 + subSequenceEnd;
                        }
                        else {
                            checkNumber = subSequenceStart1Before + subSequenceEnd;
                            if (checkNumber.charAt(0) == '0') {
                                checkNumber = checkNumber.substring(1, checkNumber.length());
                            }
                        }
                        //System.out.println("...check this: '" + checkNumber + "'");

                        NumberStatus currentPosition = this.checkThisNumber(checkNumber, this.source);
                        if (currentPosition.getStatus()) {
                            if (!found || position.compareTo(currentPosition.getPosition()) > 0) {
                                position = currentPosition.getPosition();
                                found = true;
                            }
                        }
                    }
                }

                if (!sameRank) {
                    String subSequenceStart2 = sequenceSearchPart2.substring(0, (i - j + 1) < sequenceSearchPart1.length() ? (i - j + 1) : sequenceSearchPart1.length());
                    String subSequenceSearchPart2End = sequenceSearchPart2.substring((i - j + 1) < sequenceSearchPart1.length() ? (i - j + 1) : sequenceSearchPart1.length());

                    int subSequenceStart2Length = subSequenceStart2.length();
                    BigInteger subSequenceStart2Number;
                    BigInteger subSequenceStart2NumberBefore;
                    String subSequenceStart2Before = "";
                    if (subSequenceStart2Length > 0) {
                        subSequenceStart2Number = new BigInteger(subSequenceStart2);
                        subSequenceStart2NumberBefore = subSequenceStart2Number.subtract(BigInteger.valueOf(1));
                        subSequenceStart2Before = subSequenceStart2NumberBefore.toString();
                    }

                    //System.out.println("...search~2: '" + subSequenceSearchPart2End + "' in '" + subSequenceEndContinue + "'");
                    int search2 = subSequenceEndContinue.indexOf(subSequenceSearchPart2End);
                    if (subSequenceSearchPart2End.length() == 0 || search2 == 0) {
                        String checkNumber = "";
                        checkNumber = subSequenceStart2Before + subSequenceEnd;
                        if (checkNumber.charAt(0) == '0') {
                            checkNumber = checkNumber.substring(1, checkNumber.length());
                        }
                        //System.out.println("...check this: '" + checkNumber + "'");

                        NumberStatus currentPosition = this.checkThisNumber(checkNumber, this.source);
                        if (currentPosition.getStatus()) {
                            if (!found || position.compareTo(currentPosition.getPosition()) > 0) {
                                position = currentPosition.getPosition();
                                found = true;
                            }
                        }
                    }
                }
            }

            //System.out.println("");

            // @TODO: скорректировать когда search2, а когда search1, тк получается скачка по разрядам...
            //if (found) {
            //    break;
            //}
        }

        if (position != null) {
            result = position;
        }

        return result.toString();
    }

    private NumberStatus checkThisNumber(String numberString, String sequence) {
        NumberStatus result = new NumberStatus(numberString, false, null);

        String myString = "";
        int sequenceLength = sequence.length();
        int numberStringLength = numberString.length();
        BigInteger number = new BigInteger(numberString);
        BigInteger startNumber = new BigInteger(numberString);
        while (myString.length() < (sequenceLength + numberStringLength - 1)) {
            myString = myString + number.toString();
            number = number.add(BigInteger.valueOf(1));
        }

        int positionInside = myString.indexOf(sequence);
        if (positionInside != -1 && positionInside < numberStringLength) {
            BigInteger position = this.getThisNumberPosition(startNumber).add(BigInteger.valueOf(positionInside));
            result.setPosition(position);
            result.setStatus(true);
        }

        return result;
    }

    private BigInteger getThisNumberPosition(BigInteger number) {
        BigInteger result = BigInteger.valueOf(0);

        String numberString = number.toString();
        int numberStringLength = numberString.length();

        for (int i = 1; i < numberStringLength; i++) {
            BigInteger shift = BigInteger.valueOf(i);
            BigInteger rankCount = new BigInteger("9" + new String(new char[i - 1]).replace("\0", "0"));
            rankCount = rankCount.add(BigInteger.valueOf(1));

            result = result.add(rankCount.multiply(shift));
        }

        BigInteger lastRankCount = new BigInteger("1" + new String(new char[numberStringLength - 1]).replace("\0", "0"));
        lastRankCount = number.subtract(lastRankCount);

        if (result.compareTo(BigInteger.valueOf(0)) == 0) {
            result = result.add(BigInteger.valueOf(1));
        }
        result = result.add(lastRankCount.multiply(BigInteger.valueOf(numberStringLength)));

        return result;
    }



    public String toString() {
        return this.source;
    }

}
