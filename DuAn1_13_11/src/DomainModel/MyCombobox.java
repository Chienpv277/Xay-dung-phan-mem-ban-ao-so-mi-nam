/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DomainModel;

/**
 *
 * @author FPTSHOP
 */
public class MyCombobox {
    Object id;
    
    Object ten;

    public MyCombobox(Object id, Object ten) {
        this.id = id;
        this.ten = ten;
    }

    

    @Override
    public String toString() {
        return ten.toString();
    }
    
    
    
}
