package org.poo.handlers;

public class GetUserAge {
    public static int getUserAge(final String birthDate) {
        final String[] birthDateArray = birthDate.split("-");
        final int birthYear = Integer.parseInt(birthDateArray[0]);
        final int birthMonth = Integer.parseInt(birthDateArray[1]);
        final int birthDay = Integer.parseInt(birthDateArray[2]);

        final java.util.Calendar cal = java.util.Calendar.getInstance();
        final int currentYear = cal.get(java.util.Calendar.YEAR);
        final int currentMonth = cal.get(java.util.Calendar.MONTH) + 1;
        final int currentDay = cal.get(java.util.Calendar.DATE);

        int age = currentYear - birthYear;

        if (currentMonth < birthMonth || (currentMonth == birthMonth && currentDay < birthDay)) {
            age--;
        }

        return age;
    }
}
