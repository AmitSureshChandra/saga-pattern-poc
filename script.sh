docker build -t ecommerce-saga-poc/gateway-service -f gateway-service/Dockerfile .
docker build -t ecommerce-saga-poc/product-service -f product-service/Dockerfile .
docker build -t ecommerce-saga-poc/user-service -f user-service/Dockerfile .
docker build -t ecommerce-saga-poc/order-service -f order-service/Dockerfile .
docker build -t ecommerce-saga-poc/catalog-service -f catalog-service/Dockerfile .
docker build -t ecommerce-saga-poc/payment-service -f payment-service/Dockerfile .
docker build -t ecommerce-saga-poc/event-log-service -f event-log-service/Dockerfile .
docker build -t ecommerce-saga-poc/shipping-service -f shipping-service/Dockerfile .
docker-compose up --build -d