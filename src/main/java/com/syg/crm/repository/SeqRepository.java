package com.syg.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.syg.crm.enums.SeqType;
import com.syg.crm.model.Seq;

public interface SeqRepository extends JpaRepository<Seq, Long> {

	public Seq findByType(SeqType seqType);

	public Seq findByType(String seqType);
}
