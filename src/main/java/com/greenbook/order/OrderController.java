package com.greenbook.order;

import com.greenbook.product.Product;
import com.greenbook.product.ProductCatalog;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final ProductCatalog catalog;

    public OrderController(ProductCatalog catalog) {
        this.catalog = catalog;
    }

    @GetMapping("/preview")
    public OrderPreview preview(@RequestParam long productId, @RequestParam int quantity) {
        if (quantity < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "数量必须大于 0");
        }

        Product product = catalog.findById(productId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "商品不存在"));
        BigDecimal totalAmount = product.price().multiply(BigDecimal.valueOf(quantity));
        return new OrderPreview(product.id(), quantity, product.price(), totalAmount);
    }
}
