# Malicious HTTP Traffic Defending Middleware (OpenResty Defender)

The Malicious HTTP Traffic Defending Middleware, powered by OpenResty Defender, is a powerful solution designed to
protect your web applications from various forms of malicious HTTP traffic. This middleware acts as a shield, allowing
only legitimate traffic to pass through while effectively defending against potential threats.

## Features

- **Malicious Activity Detection:**
  Designed and developed a robust middleware capable of detecting and thwarting malicious activities originating from
  specific IP addresses, effectively safeguarding application backend servers.
- **Extensible Rule Engine:**
  Implemented an extendable rule engine using the strategy design pattern. This engine manages HTTP request detection
  rules, such as identifying high-frequency IP addresses and forbidden geographical locations. The modular approach
  allows for easy addition and modification of detection rules.
- **Dynamic Rule Management:**
  Developed a mechanism for rule hot reloading by periodically fetching rule specifications from Amazon S3. This dynamic
  rule management enables real-time adjustments to detection criteria without interrupting the middleware's operation.
- **Seamless Communication with OpenResty Lua:**
  Leveraged socket programming to establish a TCP servlet, enabling seamless communication between the middleware and an
  OpenResty Lua module. This communication channel facilitates the exchange of incoming requests and the retrieval of
  verdicts based on the applied detection rules.
- **Optimized High-Frequency IP Detection:**
  Optimized the high-frequency IP detection algorithm by utilizing a Redis List data structure. This optimization
  results in improved performance and efficiency when identifying and mitigating high-frequency IP addresses engaged in
  suspicious activities.