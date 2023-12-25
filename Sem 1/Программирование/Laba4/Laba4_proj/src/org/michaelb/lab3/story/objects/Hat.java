package org.michaelb.lab3.story.objects;

import java.util.Objects;

public class Hat {

    public int size;
    public String material;

    public Hat(int size, String material) {
        this.size = size;
        this.material = material;
    }

    public void makeRefund(String reason, Hat hat, int orderNumber) {
        class Refund {
            final String reason;
            final int orderNumber;

            public Refund(String reason, int orderNumber) {
                this.reason = reason;
                this.orderNumber = orderNumber;
            }

            public void MakeHatRefund(Hat hat) {
                System.out.println("Заявка на возврат шляпы оформлена, ожидайте звонка оператора.");
            }
        }
        Refund refund = new Refund(reason, orderNumber);
        refund.MakeHatRefund(hat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hat hat = (Hat) o;
        return size == hat.size && Objects.equals(material, hat.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, material);
    }

    @Override
    public String toString() {
        return "Hat{" +
                "size=" + size +
                ", material='" + material + '\'' +
                '}';
    }
}
