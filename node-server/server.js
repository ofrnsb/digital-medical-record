const { createProxyMiddleware } = require('http-proxy-middleware');
const express = require('express');
const cors = require('cors'); // Import cors middleware

const server = express();

server.use(cors());

server.use(
  '/api',
  createProxyMiddleware({
    target: 'https://icdaccessmanagement.who.int',
    changeOrigin: true,
    pathRewrite: {
      '^/api': '',
    },
  }),
);

server.use(
  '/getdetails',
  createProxyMiddleware({
    target: 'https://id.who.int/icd/release/11/2024-01/mms',
    changeOrigin: true,
    pathRewrite: {
      '^/getdetails': '',
    },
  }),
);

server.use(
  '/getToken',
  createProxyMiddleware({
    target: 'https://icdaccessmanagement.who.int/connect/token',
    changeOrigin: true,
    pathRewrite: {
      '^/getToken': '',
    },
  })
);

server.all('*', (req, res) => {
  res.status(404).send('Not Found');
});

const port = 3001;
server.listen(port, (err) => {
  if (err) throw err;
});
