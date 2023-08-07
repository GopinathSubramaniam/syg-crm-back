package com.syg.crm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.syg.crm.enums.SeqType;
import com.syg.crm.model.Seq;
import com.syg.crm.repository.SeqRepository;

@Component
public class DataLoader implements ApplicationRunner {

	@Autowired
	private SeqRepository seqRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		List<Seq> seqs = seqRepository.findAll();
		if (seqs.isEmpty()) {
			seqs = new ArrayList<>();
			seqs.add(new Seq(SeqType.SA));
			seqs.add(new Seq(SeqType.A));
			seqs.add(new Seq(SeqType.E));
			seqs.add(new Seq(SeqType.MT));
			seqs.add(new Seq(SeqType.BR));
			seqs.add(new Seq(SeqType.TA));

			seqRepository.saveAllAndFlush(seqs);

		}

	}

}
