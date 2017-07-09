/**
 * Simulerar en bankomat. Man kan sätta in och ta ut pengar samt kontrollera saldo och senaste transaktioner.
 * @author Joakim Vallin, joaval-6
 */
import java.util.Scanner;
public class Atm
{
	private static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args)
	{
		//Deklaration av variabler
		int balance = 0; //Nuvarande saldo
		int amount = 0; //Hur mycket som ska sättas in eller tas ut.
		int[] transactions = new int[10]; //Senaste transaktioner, här kan man ändra mängden transaktioner som syns.
		int end = 0; // Check för att avsluta
		
		//Visar menyn igen så länge end är 0, dvs så länge användaren inte matar in 4 i menyn.
		while (end == 0) 
		{
			int selection = menu(); //Skriv ut menyn och hämta användarens val.
			switch (selection) //switch för att göra olika saker beroende på val.
			{
				case 1: amount = input("Hur mycket vill du sätta in? "); //Insättning
						if (amount == 0) //Kolla så att inte mängende pengar som ska sättas in är 0.
						{
							System.out.println("Du valde att inte sätta in några pengar.");
						}
						else
						{
							makeTransaction(transactions, amount); //Spara ner transaktionen
							balance += amount; //Uppdatera saldot
							System.out.println(amount + " kr sattes in."); //Skriv ut en bekräftelse till användraren på vad som gjorts.
						}
						break; //Kliv ut ur switchen.
						
				case 2: amount = input("Hur mycket vill du ta ut? "); //Uttag 
						if (balance - amount < 0) //Kolla så att kontot inte hamnar på minus.
						{
							System.out.println("Du har inte tillräckligt med pengar på kontot");
						}
						else if (amount == 0) 
						{
							System.out.println("Du valde att inte ta ut några pengar.");
						}
						else
						{
							balance -= amount;
							System.out.println(amount + " kr togs ut.");
							amount *= -1; //Gör pengamängden negativ så att den lagras så ett negativt tal i transaktionslistan.
							makeTransaction(transactions, amount);
						}
						break;
						
				case 3: showTransactions(transactions, balance); //Visa transaktioner och saldo
						break;
						
				case 4: end = 1; //Avsluta
						System.out.println("Tack för besöket");
						break;
						
				default: System.out.println("Ogiltigt val"); //Om något annat tal än 1-4 valts så visas ogiltigt val och dom får välja igen.
						 break;
			}
		}
	}
	/*
	 * Skriver ut menyn och tar emot menyval
	 * @return menyvalet
	 */
	public static int menu()
	{
		System.out.println("");
		System.out.println("1. Insättning");
		System.out.println("2. Uttag");
		System.out.println("3. Saldobesked");
		System.out.println("4. Avsluta");
		System.out.println("");
		return input ("Ditt val: ");
	}
	/*
	 * Tar emot användarens input
	 * @param msg meddelande som skrivs ut och ber användaren om input
	 * @return det heltal som användaren anger
	 */
	public static int input(String msg)
	{
		System.out.print(msg); //skriv ut meddelandet
		String trash = ""; //Variabel för att ta hand om allt som inte är heltal
		if (in.hasNextInt()) //Om det finns ett heltal så returnera absolutvärdet av det.
		{
			/*Eftersom användaren redan angivit om dom vill ta ut eller sätta in så kan vi ta det som ett positivt tal även om användaren
			tolkat det som att dom ska skriva med minustecken.*/
			return Math.abs(in.nextInt());
		}
		trash = in.next(); //Om det inte är ett heltal så spara det här
		return (input("Du angav " + trash + ". Ange ett heltal ")); //Börja om den här metoden och skriv ut vad som angivits fel.
	}
	/*
	 * Visa gjorda transaktioner
	 * @param trans arrayen med gjorda transaktioner
	 * @param balance saldot
	 */
	public static void showTransactions(int[] trans, int balance)
	{
		System.out.println("");
		System.out.println("Senaste transaktioner: ");
		for(int i = 0; i < trans.length; i++) //Gå igenom och skriv ut alla transaktioner
		{
			if (trans[i] != 0) //Om transaktionen är noll så kan vi hoppa över den.
			{
			System.out.println(trans[i]);
			}
		}
		System.out.println("Nuvarande Saldo: " + balance); //Skriv ut saldot.
	}
	/*
	 * Spara ner transaktionen för senare utskrift
	 * @param trans arrayen med gjorda transaktioner
	 * @param amount mängden pengar som har överförts.
	 */
	public static void makeTransaction(int[] trans, int amount)
	{
		int place = findNr(trans); //leta reda på vilken position i arrayen som ska skrivas till
		if (place == -1) //Om det inte finns en ledig plats flytta nuvarande transaktioner.
		{
			moveTrans(trans);
			place = trans.length - 1; //Sätt platsen att skriva på till sista platsen i arrayen
		}
		trans[place] = amount; //Spara transaktionen
	}
	/*
	 * Letar efter en ledig plats i en array
	 * @param trans arrayen som ska letas igenom
	 * @return den lediga platsen eller om det inte finns någon ledig -1
	 */
	private static int findNr(int[] trans)
	{
		for (int i = 0; i < trans.length; i++)
		{
			if (trans[i] == 0)
			{
				return i;
			}
		}		
		return -1;
	}
	/*
	 * Flytta transaktioner om transaktionsarrayen är full
	 * @param trans arrayen med transaktioner
	 */
	private static void moveTrans(int[] trans)
	{
		for (int i = 0; i < trans.length - 1; i++)//Gå igenom hela
		{
			trans[i] = trans[i + 1]; //Flytta ett steg
		}
	}
}
