package main.java.oop_fundamentals.class_problems;

import java.util.Scanner;

class HostelRoom {
    String roomNo;
    int beds;
    int occupied;

    public HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    public void allot(String name) {
        if (occupied < beds) {
            occupied++;
        }
    }
}

public class HostelMain {

    public static HostelRoom findAvailableRoom(HostelRoom[] rooms) {
        if (rooms == null) {
            return null;
        }
        for (HostelRoom room : rooms) {
            if (room != null && room.occupied < room.beds) {
                return room;
            }
        }
        return null;
    }

    public static void safeAllot(HostelRoom[] rooms, String studentName) {
        HostelRoom availableRoom = findAvailableRoom(rooms);
        if (availableRoom != null) {
            availableRoom.allot(studentName);
            System.out.println(studentName + " allotted to room " + availableRoom.roomNo);
        } else {
            System.out.println("No rooms available for " + studentName);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numRooms = scanner.nextInt();
        HostelRoom[] rooms = new HostelRoom[numRooms];

        for (int i = 0; i < numRooms; i++) {
            String roomNo = scanner.next();
            int beds = scanner.nextInt();
            int occupied = scanner.nextInt();
            rooms[i] = new HostelRoom(roomNo, beds, occupied);
        }

        String studentName = scanner.next();

        safeAllot(rooms, studentName);

        scanner.close();
    }
}