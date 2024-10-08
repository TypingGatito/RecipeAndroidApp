package com.shoe_store.services;

import com.shoe_store.repositories.admin.IAdminRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminService {

    private final IAdminRepository adminRepository;
}
