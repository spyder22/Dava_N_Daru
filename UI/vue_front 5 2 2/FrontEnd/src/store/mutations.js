export const SET_PRODUCTS = (state, products) => {
  state.products = products
}

export const SET_PRODUCTS_BY_CATEGORY_DRINK = (state, products) => {
  state.products = products
}
export const SET_PRODUCTS_BY_CATEGORY_CIGAR = (state, products) => {
  state.products = products
}
export const SET_PRODUCTS_BY_CATEGORY_FOOD = (state, products) => {
  state.products = products
}
export const SET_PRODUCTS_BY_CATEGORY_MEDICINE = (state, products) => {
  state.products = products
}
export const SET_PRODUCTS_BY_CATEGORY_GAMING = (state, products) => {
  state.products = products
}
export const SET_RECOMMENDATIONS = (state, recommendations) => {
  state.recommendations = recommendations
}

export const SET_PRODUCT = (state, product) => {
  state.product = product
}

export const ADD_TO_CART = (state, {product, quantity}) => {
  let productInCart = state.cart.find(item => {
    return item.product.productId === product.productId
  })
  if (productInCart) {
    productInCart.quantity += quantity
    return
  }
  state.cart.push({
    product,
    quantity
  })
}
export const SET_CART = (state, cartItems) => {
  state.cart = cartItems
}

export const REMOVE_PRODUCT_FROM_CART = (state, product) => {
  state.cart = state.cart.filter(item => {
    return item.product.productId !== product.productId
  })
}
