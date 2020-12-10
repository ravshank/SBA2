package com.iiht.training.eloan.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iiht.training.eloan.dto.LoanDto;
import com.iiht.training.eloan.dto.LoanOutputDto;
import com.iiht.training.eloan.dto.ProcessingDto;
import com.iiht.training.eloan.dto.SanctionOutputDto;
import com.iiht.training.eloan.dto.UserDto;
import com.iiht.training.eloan.entity.Loan;
import com.iiht.training.eloan.entity.ProcessingInfo;
import com.iiht.training.eloan.entity.SanctionInfo;
import com.iiht.training.eloan.entity.Users;
import com.iiht.training.eloan.repository.LoanRepository;
import com.iiht.training.eloan.repository.ProcessingInfoRepository;
import com.iiht.training.eloan.repository.SanctionInfoRepository;
import com.iiht.training.eloan.repository.UsersRepository;
import com.iiht.training.eloan.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private UsersRepository usersRepository;
	
	@Autowired
	private LoanRepository loanRepository;
	
	@Autowired
	private ProcessingInfoRepository ProcessingInfoRepository;
	
	@Autowired
	private SanctionInfoRepository sanctionInfoRepository;
	
	
	private LoanOutputDto convertLoanEntityToOutputDto(Loan loanApplied, LoanDto loanInputDto, SanctionOutputDto sanctionOutputDto, ProcessingDto processingDto) 
	{
		LoanOutputDto loanOutputDto = new LoanOutputDto();
		loanOutputDto.setCustomerId(loanApplied.getCustomerId());
		loanOutputDto.setLoanAppId(loanApplied.getId());
		loanOutputDto.setLoanDto(loanInputDto);
		loanOutputDto.setRemark(loanApplied.getRemark());
		loanOutputDto.setStatus(loanApplied.getStatus());
		loanOutputDto.setUserDto(convertUsersEntitytoUserDto(this.usersRepository.getOne(loanApplied.getCustomerId())));
		
		loanOutputDto.setProcessingDto(processingDto);
		loanOutputDto.setSanctionOutputDto(sanctionOutputDto);
		
		return loanOutputDto;
	}
	
	
	private Users convertUserDtotoEntitycustomer(UserDto userdto) {
		Users user = new Users();
		user.setFirstName(userdto.getFirstName());
		user.setLastName(userdto.getLastName());
		user.setId(userdto.getId());
		user.setEmail(userdto.getEmail());
		user.setMobile(userdto.getMobile());
		user.setRole("Customer");
		return user;
	}
	
	private UserDto convertUsersEntitytoUserDto(Users users) {
		UserDto userdto = new UserDto();
		userdto.setId(users.getId());
		userdto.setFirstName(users.getFirstName());
		userdto.setLastName(users.getLastName());
		userdto.setEmail(users.getEmail());
		userdto.setMobile(users.getMobile());
		//String role = users.getRole();
		return userdto;
	}
	
	private Loan convertLoanInputDtoToEntity(Long customerId, LoanDto loanInputDto) 
	{
		Loan loan = new Loan();
		loan.setLoanName(loanInputDto.getLoanName());
		loan.setLoanAmount(loanInputDto.getLoanAmount());
		loan.setCustomerId(customerId);
		loan.setLoanApplicationDate(loanInputDto.getLoanApplicationDate());
		loan.setBusinessStructure(loanInputDto.getBusinessStructure());
		loan.setBillingIndicator(loanInputDto.getBillingIndicator());
		loan.setTaxIndicator(loanInputDto.getTaxIndicator());
		loan.setRemark("Applying Loan");
		loan.setStatus(0);
		return loan;
	}
	
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
	
	private SanctionOutputDto convertSanctionInfoEntityToOutputDto(SanctionInfo sanctionInfo) 
	{
		SanctionOutputDto sanctionOutputDto = new SanctionOutputDto();
		sanctionOutputDto.setLoanAmountSanctioned(sanctionInfo.getLoanAmountSanctioned());
		sanctionOutputDto.setLoanClosureDate(sanctionInfo.getLoanClosureDate());
		sanctionOutputDto.setMonthlyPayment(sanctionInfo.getMonthlyPayment());
		sanctionOutputDto.setPaymentStartDate(sanctionInfo.getPaymentStartDate());
		sanctionOutputDto.setTermOfLoan(sanctionInfo.getTermOfLoan());
		/*
		 * sanctionOutputDto.setInterestRate(sanctionInfo.getInterestRate());
		 * sanctionOutputDto.setRemarks(sanctionInfo.getRemarks());
		 */
		return sanctionOutputDto;
		
	}
	
	
	@Override
	public UserDto register(UserDto userDto) {
		// TODO Auto-generated method stub
		
		// ravinder code convert dto into entity
				Users user = this.convertUserDtotoEntitycustomer(userDto);
				//save this to db
				Users newCustomer = this.usersRepository.save(user);
				//convert entity to dto
				UserDto customerout = this.convertUsersEntitytoUserDto(newCustomer);
				return customerout;
			}


	@Override
	public LoanOutputDto applyLoan(Long customerId, LoanDto loanDto) {
		// convert loan dto entity
		Loan loan = this.convertLoanInputDtoToEntity(customerId, loanDto);
		//save this to db
		Loan appliedloan = this.loanRepository.save(loan);
		//convert entity to dto
		LoanOutputDto loanoutput = this.convertLoanEntityToOutputDto(appliedloan, loanDto, null, null);
		return loanoutput;
	}
	

	@Override
	public LoanOutputDto getStatus(Long loanAppId) {
		// ravinder
		Loan loanFetched = this.loanRepository.getOne(loanAppId);
		LoanDto loanDto= new LoanDto();
		loanDto.setLoanName(loanFetched.getLoanName());
		loanDto.setLoanApplicationDate(loanFetched.getLoanApplicationDate());
		loanDto.setBusinessStructure(loanFetched.getBusinessStructure());
		loanDto.setBillingIndicator(loanFetched.getBillingIndicator());
		loanDto.setTaxIndicator(loanFetched.getTaxIndicator());
		loanDto.setLoanAmount(loanFetched.getLoanAmount());
		

		ProcessingDto processingDto = convertProcessingEntityToOutputDto(this.ProcessingInfoRepository.checkExistsByLoanId(loanAppId).get(0));
		SanctionOutputDto sanctionOutputDto = convertSanctionInfoEntityToOutputDto(this.sanctionInfoRepository.checkExistsByLoanId(loanAppId).get(0));
		
		return convertLoanEntityToOutputDto(loanFetched, loanDto, sanctionOutputDto, processingDto);
		
	}

	@Override
	public List<LoanOutputDto> getStatusAll(Long customerId) {
		// TODO Auto-generated method stub
       List<Loan> customerloan = this.loanRepository.findAll();
       List<LoanOutputDto> loandetails = customerloan.stream()
    		                             .filter(custid -> custid.getCustomerId().equals(customerId))
    		                             .map(this :: convertLoanEntitytoLoanOutputDto)
    		                             .collect(Collectors.toList());
       
    		                             
       
       return loandetails;
	
	}

}
