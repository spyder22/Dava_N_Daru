import axios from 'axios'
export const getProducts = ({ commit }) => {
  axios.get(`http://localhost:8083/product/cat/Drinks`)
    .then(response => {
      commit('SET_PRODUCTS', response.data)
    })
}
export const getProductsByCategoryDrink = ({ commit }) => {
  axios.get(`http://localhost:8083/product/cat/Drinks`)
    .then(response => {
      commit('SET_PRODUCTS_BY_CATEGORY_DRINK', response.data)
    })
}
export const getProductsByCategoryCigar = ({ commit }) => {
  axios.get(`http://localhost:8083/product/cat/Cigarette`)
    .then(response => {
      commit('SET_PRODUCTS_BY_CATEGORY_CIGAR', response.data)
    })
}
export const getProductsByCategoryFood = ({ commit }) => {
  axios.get(`http://localhost:8083/product/cat/Food`)
    .then(response => {
      commit('SET_PRODUCTS_BY_CATEGORY_FOOD', response.data)
    })
}

export const getProductsByCategoryMedicine = ({ commit }) => {
  axios.get(`http://localhost:8083/product/cat/Healtcare`)
    .then(response => {
      commit('SET_PRODUCTS_BY_CATEGORY_MEDICINE', response.data)
    })
}
export const getProductsByCategoryGaming = ({ commit }) => {
  axios.get(`http://localhost:8083/product/cat/Game`)
    .then(response => {
      commit('SET_PRODUCTS_BY_CATEGORY_GAMING', response.data)
    })
}

export const getProduct = ({ commit }, productId) => {
  axios.get(`http://localhost:8083/product/${productId}`)
    .then(response => {
      commit('SET_PRODUCT', response.data)
    })
}

// eslint-disable-next-line standard/object-curly-even-spacing
export const addProductToCart = ({ commit }, {product, quantity, userEmail, merchantId }) => {
  console.log('action started')
  commit('ADD_TO_CART', {product, quantity, userEmail, merchantId})
  axios.post('http://localhost:8084/cart/add', {
    productId: product.productId,
    quantity,
    userEmail,
    merchantId
  })
}
export const getCartItemByEmail = ({commit}, email) => {
  email = localStorage.getItem('email')
  console.log(email)
  axios.get(`http://localhost:8084/cart/get/${email}`)
    .then(response => {
      commit('SET_CART', response.data)
    })
}
export const removeProductFromCart = ({ commit }, product) => {
  commit('REMOVE_PRODUCT_FROM_CART', product)
  axios.delete(`http://localhost:8084/cart/remove${product.productId}`)
}
export const getRecommendations = ({ commit }) => {
  axios.get('http://localhost:8083/product/recommends')
    .then(response => {
      commit('SET_RECOMMENDATIONS', response.data)
    })
}
