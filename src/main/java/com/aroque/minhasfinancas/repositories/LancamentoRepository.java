package com.aroque.minhasfinancas.repositories;

import com.aroque.minhasfinancas.enums.TipoLancamentoEnum;
import com.aroque.minhasfinancas.model.LancamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface LancamentoRepository extends JpaRepository<LancamentoModel, Long> {

    @Query(value = " SELECT sum(l.valor) FROM LancamentoModel l JOIN l.usuario u "
                 + " WHERE u.id = :idUsuario and l.tipo = :tipo GROUP BY u ")
    BigDecimal obterSaldoPorTipoLancamentoEUsuario(@Param("idUsuario") Long idUsuario, @Param("tipo") TipoLancamentoEnum tipo);
    // Ele colocou String no tipo, se der errado pode ser aq
}
