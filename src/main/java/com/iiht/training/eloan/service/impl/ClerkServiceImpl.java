package com.iiht.training.eloan.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.ProcessingDto;
import com.iiht.training.eloan.dto.exception.ExceptionResponse;
import com.iiht.training.eloan.entity.Loan;
import com.iiht.training.eloan.entity.ProcessingInfo;
import com.iiht.training.eloan.repository.LoanRepository;
import com.iiht.training.eloan.repository.ProcessingInfoRepository;
import com.iiht.training.eloan.repository.SanctionInfoRepository;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.ClerkService;


@Service
public class ClerkServiceImpl implements ClerkService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private ProcessingInfoRepository ProcessingInfoRepository;
	
	@Autowired
	private SanctionInfoRepository sanctionInfoRepository;
	
	private LoanOutputDto convertLoanEntitytoLoanOutputDto(Loan loan)
	{
		LoanOutputDto loutput = new LoanOutputDto(); 
		loutput.setCustomerId(loan.getCustomerId());
		loutput.setLoanAppId(loan.getId());
		loutput.setStatus(loan.getStatus());
		loutput.setRemark(loan.getRemark());
		return loutput;
	}
	
	private ProcessingDto convertProcessingEntityToOutputDto(ProcessingInfo processingInfo) 
	{
		ProcessingDto processingDto = new ProcessingDto();
		processingDto.setAcresOfLand(processingInfo.getAcresOfLand());
		processingDto.setAddressOfProperty(processingInfo.getAddressOfProperty());
		processingDto.setAppraisedBy(processingInfo.getAppraisedBy());
		processingDto.setLandValue(processingInfo.getLandValue());
		processingDto.setValuationDate(processingInfo.getValuationDate());
		processingDto.setSuggestedAmountOfLoan(processingInfo.getSuggestedAmountOfLoan());	
		return processingDto;
	}
	
	private ProcessingInfo convertProcessingInputDtoToEntity(ProcessingDto processingDto, Long clerkId, Long loanAppId) 
	{
		ProcessingInfo processingEntity = new ProcessingInfo();
		processingEntity.setLoanAppId(loanAppId);
		processingEntity.setLoanClerkId(clerkId);
  	    processingEntity.setSuggestedAmountOfLoan(processingDto.getSuggestedAmountOfLoan());
		processingEntity.setValuationDate(processingDto.getValuationDate());
		processingEntity.setLandValue(processingDto.getLandValue());
		processingEntity.setAcresOfLand(processingDto.getAcresOfLand());
		processingEntity.setAddressOfProperty(processingDto.getAddressOfProperty());
		processingEntity.setAppraisedBy(processingDto.getAppraisedBy());
		return processingEntity;
	}
	
	@Override
	public List<LoanOutputDto> allAppliedLoans() {
		// TODO Auto-generated method stub
	     List<Loan> customerloan = this.loanRepository.findAll();
	       List<LoanOutputDto> loandetails = customerloan.stream()
	    		                             .filter(status -> status.getStatus().equals(0))
	    		                             .map(this :: convertLoanEntitytoLoanOutputDto)
	    		                             .collect(Collectors.toList());
	       
	    		                             
	       
	       return loandetails;
	
	}

	@Override
	public ProcessingDto processLoan(Long clerkId, Long loanAppId, ProcessingDto processingDto) {
		// TODO Auto-generated method stub
	
		ProcessingInfo processingEntity=null;
		List<ProcessingInfo> list_processingInfo = this.ProcessingInfoRepository.checkExistsByLoanId(loanAppId);
		
		System.out.println(list_processingInfo);
		if(loanAppId!=null)
		{
			if(list_processingInfo.isEmpty())
			{	
				processingEntity= this.ProcessingInfoRepository.save(convertProcessingInputDtoToEntity(processingDto, clerkId, loanAppId));
				this.loanRepository.UpdateLoanStatus(loanAppId,1,"loan processing done");
				return convertProcessingEntityToOutputDto(processingEntity);				
			}
			
		}
		
		return null;
	}
		
	

}
