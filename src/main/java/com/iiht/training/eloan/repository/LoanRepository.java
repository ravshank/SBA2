package com.iiht.training.eloan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.iiht.training.eloan.entity.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long>{
	
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query(value = "Update loan l Set l.status = :status, l.remark = :remark WHERE l.loan_id = :loanAppId", nativeQuery = true)
	public void UpdateLoanStatus(@Param("loanAppId") Long loanAppId,@Param("status") Integer status, @Param("remark") String remark);

}
