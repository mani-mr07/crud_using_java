package net.javaguides.bank.repository;

import net.javaguides.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository  extends JpaRepository <Account, Long> {
}
