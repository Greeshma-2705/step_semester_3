class BookInventory {
private int copiesTotal;
private int copiesAvailable;

public BookInventory(int copiesTotal) {

    if (copiesTotal <= 0) {
        throw new IllegalArgumentException(
                "Total copies must be greater than zero");
    }

    this.copiesTotal = copiesTotal;
    this.copiesAvailable = copiesTotal;
}

public void checkOut() {

    if (copiesAvailable > 0) {
        copiesAvailable--;
    } else {
        System.out.println(
                "No copies available.");
    }
}

public void checkIn() {

    if (copiesAvailable < copiesTotal) {
        copiesAvailable++;
    } else {
        System.out.println(
                "All copies are already in the library.");
    }
}

public int getCopiesTotal() {
    return copiesTotal;
}

public int getCopiesAvailable() {
    return copiesAvailable;
}


}

public class Book {
public static void main(String[] args) {

    BookInventory book =
            new BookInventory(3);

    System.out.println(
            "Total copies: "
                    + book.getCopiesTotal());

    System.out.println(
            "Available copies: "
                    + book.getCopiesAvailable());

    book.checkOut();

    System.out.println(
            "After checkout: "
                    + book.getCopiesAvailable());

    book.checkOut();

    System.out.println(
            "After checkout: "
                    + book.getCopiesAvailable());

    book.checkIn();

    System.out.println(
            "After check-in: "
                    + book.getCopiesAvailable());

    book.checkIn();
    book.checkIn();
    book.checkIn();

    System.out.println(
            "Final available copies: "
                    + book.getCopiesAvailable());
}


}
