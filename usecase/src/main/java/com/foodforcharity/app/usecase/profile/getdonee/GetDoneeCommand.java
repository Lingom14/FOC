package com.foodforcharity.app.usecase.profile.getdonee;

import com.foodforcharity.app.domain.entity.Donee;
import com.foodforcharity.app.domain.reponse.Response;
import com.foodforcharity.app.mediator.Command;

import lombok.Getter;

@Getter
public class GetDoneeCommand implements Command<Response<Donee>> {
	long personId;

	/**
	 * Public Constructor
	 * 
	 * @param personId
	 */
	public GetDoneeCommand(long personId) {
		this.personId = personId;
	}

}