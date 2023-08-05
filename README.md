# Malicious HTTP Traffic Defending Middleware (OpenResty Defender)

The Malicious HTTP Traffic Defending Middleware, powered by OpenResty Defender, is a powerful solution designed to
protect your web applications from various forms of malicious HTTP traffic. This middleware acts as a shield, allowing
only legitimate traffic to pass through while effectively defending against potential threats.

## Features

#### 1. Malicious Activity Detection

Designed and developed a robust middleware capable of detecting and thwarting malicious activities originating from
specific IP addresses, effectively safeguarding application backend servers.

#### 2. Extensible Rule Engine

Implemented an extendable rule engine using the strategy design pattern. This engine manages HTTP request detection
rules, such as identifying high-frequency IP addresses and forbidden geographical locations. The modular approach
allows for easy addition and modification of detection rules.

#### 3. Dynamic Rule Management

Developed a mechanism for rule hot reloading by periodically fetching rule specifications from Amazon S3. This dynamic
rule management enables real-time adjustments to detection criteria without interrupting the middleware's operation.

#### 4. Seamless Communication with OpenResty Lua

Leveraged socket programming to establish a TCP servlet, enabling seamless communication between the middleware and an
OpenResty Lua module. This communication channel facilitates the exchange of incoming requests and the retrieval of
verdicts based on the applied detection rules.

#### 5. Optimized High-Frequency IP Detection

Optimized the high-frequency IP detection algorithm by utilizing a Redis List data structure. This optimization
results in improved performance and efficiency when identifying and mitigating high-frequency IP addresses engaged in
suspicious activities.

## Getting Started

### Prerequisites

Before you begin, ensure you have the following prerequisites in place:

- [OpenResty](https://openresty.org/): Make sure you have OpenResty installed on your system.

- [Lua](https://www.lua.org/): Ensure you have Lua installed. It's a scripting language used for customizing the middleware's behavior.

- [AWS CLI](https://aws.amazon.com/cli/): If you plan to use AWS services for storage or other operations, install the AWS CLI to manage your AWS credentials.

### Installation

#### 1. Clone the Repository

Start by cloning this repository to your local machine:

```
git clone https://github.com/YXShang97/OpenRestyDefender.git
```

#### 2. Configuration

If you wish to implement rule hot reloading by periodically fetching rule specifications from Amazon S3, follow these steps:

- **Set Up AWS S3 Bucket**: Create an Amazon S3 bucket to store your rule specification files.

- **Configure AWS Credentials**: Add your AWS access key and secret key to your `application.properties` file:

```ini
cd OpenRestyDefender/src/main/resources/
```

Replace `XXXXXXXXXXXXXXXXXXXX` with your actual AWS access key, and `XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX` with your AWS secret key:

```ini
cloud.aws.credentials.access-key=XXXXXXXXXXXXXXXXXXXX
cloud.aws.credentials.secret-key=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

Note: Make sure to keep your AWS credentials secure. Do not share them in public repositories or insecure locations.

#### 3. Integration

Add the middleware to your application's OpenResty configuration. For example, in your Nginx server block:

```nginx.conf
server {
    listen 999;
    server_name yourdomain.com;

    location / {
        rewrite_by_lua_block {
            .......
        }
    }
}
```
