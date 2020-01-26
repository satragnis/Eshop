package com.example.eshop.Network.Interfaces;

import com.example.eshop.Model.MainItems;

public interface ItemInterface {
    void onItemResponseSuccess (MainItems mainItems);
    void onItemResponseError(String message);
}
