package guru.springframework.sfgpetclinic.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
	
	@Mock 
	OwnerService ownerService;
	@Mock 
	Owner owner;
	@Mock 
	BindingResult result;
	
	@InjectMocks 
	OwnerController controller;
	
	@DisplayName("Test Process Creation Form has Errors")
	@Test
	void testProcessCreationFormHasErrors() {
		//given
		given(result.hasErrors()).willReturn(true);
		
		//when
		String rtn = controller.processCreationForm(owner, result);
		
		//then
		assertThat(rtn.equals("owners/createOrUpdateOwnerForm"));
	}

	@DisplayName("Test Process Creation Form has No Errors")
	@Test
	void testProcessCreationFormNoErrors() {
		//given
		owner.setId(5L);
		given(result.hasErrors()).willReturn(false);
		given(ownerService.save(owner)).willReturn(owner);
		
		//when
		String rtn = controller.processCreationForm(owner, result);		
		
		//then
		then(ownerService).should(times(1)).save(owner);
		assertThat(rtn.equals("redirect:/owners/5"));
	}

}
