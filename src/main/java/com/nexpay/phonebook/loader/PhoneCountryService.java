package com.nexpay.phonebook.loader;

import com.nexpay.phonebook.tree.PhoneTree;

import java.io.IOException;

public interface PhoneCountryService {

    PhoneTree scrapePhoneTree() throws IOException;
}
