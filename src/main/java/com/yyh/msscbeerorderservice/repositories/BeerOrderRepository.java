/*
 *  Copyright 2019 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.yyh.msscbeerorderservice.repositories;



import com.yyh.msscbeerorderservice.domain.BeerOrder;
import com.yyh.msscbeerorderservice.domain.Customer;
import com.yyh.msscbeerorderservice.domain.BeerOrderStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.UUID;


public interface BeerOrderRepository  extends JpaRepository<BeerOrder, UUID> {

    Page<BeerOrder> findAllByCustomer(Customer customer, Pageable pageable);

    List<BeerOrder> findAllByOrderStatus(BeerOrderStatusEnum beerOrderStatusEnum);

//    /*
//    * obtains a lock on the data until the transaction is completed.
//    * This prevents other transactions from making any updates to the entity until the lock is released.
//    * */
//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    // findOneById >>> ensure that there is only one or no value, if there are 2 values an exception will be thrown
//    BeerOrder findOneById(UUID id);
}
