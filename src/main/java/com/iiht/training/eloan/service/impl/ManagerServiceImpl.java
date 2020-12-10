package com.iiht.training.eloan.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.LoanDto;
import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.ProcessingDto;
import com.iiht.training.eloan.dto.RejectDto;
import com.iiht.training.eloan.dto.SanctionDto;
import com.iiht.training.eloan.dto.SanctionOutputDto;
import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.entity.Loan;
import com.iiht.training.eloan.repository.LoanRepository;
import com.iiht.training.eloan.repository.ProcessingInfoRepository;
import com.iiht.training.eloan.repository.SanctionInfoRepository;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {
	
	private LoanOutputDto convertLoanEntitytoLoanOutputDto(Loan loan)
	{
		LoanOutputDto loutput = new LoanOutputDto(); 
		loutput.setCustomerId(loan.getCustomerId());
		loutput.setLoanAppId(loan.getId());
		loutput.setStatus(loan.getStatus());
		loutput.setRemark(loan.getRemark());
		return loutput;
	}

	private Loan convertLoanInputDtoToEntity_Reject(Long managerId, Long loanAppId) 
	{
		Loan loan = new Loan();
		RejectDto reject = new RejectDto();
        loan.setId(loanAppId);
		loan.setRemark(reject.getRemark());;
		loan.setStatus(-1);
		return loan;
	}
	private RejectDto convertLoanEntityToOutputDtoReject(Loan loanApplied) 
	{
		LoanOutputDto loanOutputDto = new LoanOutputDto();
		RejectDto rejectdto = new RejectDto();
		loanOutputDto.setCustomerId(loanApplied.getCustomerId());
		loanOutputDto.setLoanAppId(loanApplied.getId());
		loanOutputDto.setRemark(loanApplied.getRemark());
		loanOutputDto.setStatus(loanApplied.getStatus());
	
		return rejectdto;
	}
	
	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private ProcessingInfoRepository ProcessingInfoRepository;
	
	@Autowired
	private SanctionInfoRepository sanctionInfoRepository;
	
	@Override
	public List<LoanOutputDto> allProcessedLoans() {
		// TODO Auto-generated method stub
	     List<Loan> customerloan = this.loanRepository.findAll();
	       List<LoanOutputDto> loandetails = customerloan.stream()
	    		                             .filter(status -> status.getStatus().equals(1))
	    		                             .map(this :: convertLoanEntitytoLoanOutputDto)
	    		                             .collect(Collectors.toList());
	       
	    		                             
	       
	       return loandetails;
	
	}

	@Override
	public RejectDto rejectLoan(Long managerId, Long loanAppId, RejectDto rejectDto) {
		
		 Loan loan = this.convertLoanInputDtoToEntity_Reject(managerId, loanAppId);
		 Loan updateloan = this.loanRepository.save(loan);
		 RejectDto reject = this.convertLoanEntityToOutputDtoReject(updateloan);
				 
         return reject ;
	}

	@Override
	public SanctionOutputDto sanctionLoan(Long managerId, Long loanAppId, SanctionDto sanctionDto) {
		// TODO Auto-generated method stub
		return null;
	}

}
