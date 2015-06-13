// Nick Shen
// SinglyLinkedList.java
// 5/20/14
// This program is a LinkedList class that has the fuctions of a basic LinkedList.
// It allows you to get the first and last element in the LinkedList, add the first
// or last element in the list, load the data, insert, remove, and clear.

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SinglyLinkedList {
    private ListNode first, last;
    private Scanner reader;

    public SinglyLinkedList() {
        first = null;
        last = null;
    }

    //returns the first object
    public Object getFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        } else {
            return first.getValue();
        }
    }

    //returns the last object
    public Object getLast()
    {
        if (last == null) {
            throw new NoSuchElementException();
        } else {
            return last.getValue();
        }
    }

    //adds an object to the itt of the LinkedList
    public void addFirst(Object value)
    {
        if (getSize() == 0) {
            last = first = new ListNode(value);
        }
        else {
            first = new ListNode(value, first);
        }
    }

    //takes a value and adds it as a node to the end of the arraylist
    public void addLast(Object value)
    {
        if (getSize() == 0) {
            last = first = new ListNode(value);
        }
        else {
            ListNode previousLast = last;
            last = new ListNode(value);
            previousLast.setNext(last);
        }
    }

    //gives the size of the LinkedList
    public int getSize()
    {
        int count = 0;
        ListNode temp = first;
        while (temp != null) {
            count++;
            temp = temp.getNext();
        }
        return count;
    }

    //prints out the listnode list
    public void printList()
    {
        ListNode temp = first;
        while (temp != null) {
            System.out.print(temp.getValue() + " \n");
            temp = temp.getNext();
        }
    }

    //loads the data from the text file 
    public void loadData()
    {
        String fileToOpen = Prompt.getString("Enter a file to open: ");
        if (getSize() <= 0) {
            reader = OpenFile.OpenToRead(fileToOpen);
            while (reader.hasNextLine()) {
                Item it = new Item(reader.nextInt(), reader.nextInt());
                insert(it);
            }
        }
    }

    //inserts the given value and sorts it by ID
    public void insert(Item it)
    {
        if (first != null) {
            if (it.compareTo(first.getValue()) <= 0)
                addFirst(it);
            else if (last != null && it.compareTo(last.getValue()) >= 0)
                addLast(it);
            else {
                ListNode temp = first;
                ListNode prevTemp = temp;
                int count = 0;
                while (count < getSize() && it.compareTo(temp.getValue()) >  0) {
                    count++;
                    prevTemp = temp;
                    temp = temp.getNext();
                }
                insert(it, prevTemp, temp);
            }
        } else
            addFirst(it);
    }

    // inserts a node between two nodes
    public void insert(Item it, ListNode previous, ListNode subsequent)
    {
        ListNode itEntry = new ListNode(it, subsequent);
        previous.setNext(itEntry);
    }

    // clear the ListNode by setting it as null
    public void clear() {
        first = null;
        last = null;
    }

    // method for deleting data from the ListNode
    public void testDelete() {
        String target = Prompt.getString("Enter an ID number to delete (Q to quit): ");
        if (!(target.equals("Q")) && !(target.equals("q"))) {
            ListNode deleted = remove(Integer.parseInt(target));
            if (deleted == null)
                System.out.println("\n" + target + " could not be found, so was not deleted.\n");
            else
                System.out.println("\n" + target + " was deleted.\n");
            testDelete();
        }
    }

    // Remove a string in a node from the ListNode
    public ListNode remove(int id) {
        ListNode temp = first;
        ListNode prevTemp = temp;
        for (int i = 0; i < getSize(); i++) {
            if (((Item)(temp.getValue())).getId() == (id)) {
                if (i == 0)
                    first = new ListNode(first.getNext().getValue(), first.getNext().getNext());
                else if (i >= getSize() - 1)
                    prevTemp.setNext(null);
                else
                    prevTemp.setNext(temp.getNext());
                return temp;
            }
            prevTemp = temp;
            temp = temp.getNext();
        }
        return null;
    }

    // The method to find the data value in the file.
    public void testFind() {
        String target = Prompt.getString("Enter an ID to search (Q to quit): ");
        if (!(target.equals("Q")) && !(target.equals("q"))) {
            ListNode found = search(Integer.parseInt(target));
            if (found == null)
                System.out.println("\n" + target + " could not be found.\n");
            else
                System.out.println("\n" + found.getValue() + "\n");
            testFind();
        }
    }

    // Searching method for the class
    public ListNode search(int id)
    {
        ListNode temp = first;
        for (int i = 0; i < getSize(); i++) {
            if (((Item)(temp.getValue())).getId() == id)
                return temp;
            temp = temp.getNext();
        }
        return null;
    }

    // prints out the list backwards using recursion
    public void printBackwards()
    {
        System.out.println(helper(first, 0));
    }

    // helper method to print out the list in reverse order
    public String helper(ListNode l, int count)
    {
        if (count >= getSize())
            return "";
        return helper(l.getNext(), count + 1) + "\n" + l.getValue().toString();
    }
}