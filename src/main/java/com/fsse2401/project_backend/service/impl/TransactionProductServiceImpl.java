package com.fsse2401.project_backend.service.impl;

import com.fsse2401.project_backend.data.cartItem.entity.CartItemEntity;
import com.fsse2401.project_backend.data.transaction.entity.TransactionEntity;
import com.fsse2401.project_backend.data.transactionProduct.entity.TransactionProductEntity;
import com.fsse2401.project_backend.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project_backend.data.user.entity.UserEntity;
import com.fsse2401.project_backend.repository.TransactionProductRepository;
import com.fsse2401.project_backend.service.TransactionProductService;
import com.fsse2401.project_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionProductServiceImpl implements TransactionProductService {
    private final UserService userService;
    private final TransactionProductRepository transactionProductRepository;
    @Autowired
    public TransactionProductServiceImpl(UserService userService,
                                         TransactionProductRepository transactionProductRepository) {
        this.userService = userService;
        this.transactionProductRepository = transactionProductRepository;
    }

    @Override
    public List<TransactionProductEntity> putTransactionProduct(FirebaseUserData firebaseUserData,
                                            TransactionEntity transactionEntity,
                                            List<CartItemEntity> cartItemEntityList){
        // Get user entity
        UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);

        // Initialize a total price for the transaction
//        BigDecimal total = BigDecimal.ZERO;
        // Create transaction product and store into database, add subtotal to total
        for(CartItemEntity cartItemEntity: cartItemEntityList){
//            total = total.add(TransactionProductDataUtil.getSubtotal(cartItemEntity));
            transactionProductRepository.save(new TransactionProductEntity(
                    transactionEntity, cartItemEntity
            ));
        }
        // return list
        List<TransactionProductEntity> transactionProductEntityList = new ArrayList<>();
        for (TransactionProductEntity transactionProductEntity: transactionProductRepository.findAllByTansaction(transactionEntity)){
            transactionProductEntityList.add(transactionProductEntity);
        }
        return transactionProductEntityList;
    }

    @Override
    public List<TransactionProductEntity> getTransactionProductEntityLsitByTransaction(TransactionEntity transactionEntity){
        return transactionProductRepository.findAllByTansaction(transactionEntity);
    }


}
